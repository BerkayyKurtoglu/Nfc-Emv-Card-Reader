package com.berkay.nfcemvcardreader.emvreader.parser.impl

import android.util.Log
import com.berkay.nfcemvcardreader.emvreader.enums.CommandEnum
import com.berkay.nfcemvcardreader.emvreader.exceptions.CommunicationException
import com.berkay.nfcemvcardreader.emvreader.iso7816emv.TagAndLength
import com.berkay.nfcemvcardreader.emvreader.iso7816emv.tag.EmvTag
import com.berkay.nfcemvcardreader.emvreader.model.EmvTransactionRecord
import com.berkay.nfcemvcardreader.emvreader.model.enums.CurrencyEnum
import com.berkay.nfcemvcardreader.emvreader.parser.EmvTemplate
import com.berkay.nfcemvcardreader.emvreader.parser.IParser
import com.berkay.nfcemvcardreader.emvreader.utils.CommandApdu
import com.berkay.nfcemvcardreader.emvreader.utils.ResponseUtils
import com.berkay.nfcemvcardreader.emvreader.utils.TlvUtil
import com.berkay.nfcemvcardreader.emvreader.utils.TrackUtils
import com.berkay.nfcemvcardreader.emvreader.utils.bitutils.BytesUtils
import java.lang.ref.WeakReference

private const val UNKNOWN = -1
private const val TAG = "AbstractParser"

/**
 * Abstract base parser for EMV cards. Provides shared logic for Visa/MasterCard parsers.
 */
abstract class AbstractParser(
    template: EmvTemplate,
) : IParser {

    val template = WeakReference(template)

    /**
     * Sends a SELECT command to the card with given AID or RID.
     */
    @Throws(CommunicationException::class)
    protected fun selectAID(aid: ByteArray): ByteArray {
        Log.d(TAG, "Select AID: ${BytesUtils.bytesToString(aid)}")
        return template.get()?.provider?.transceive(
            CommandApdu(CommandEnum.SELECT, aid, 0).toBytes()
        ) ?: ByteArray(0)
    }

    /**
     * Extracts the application label (e.g., VISA, MasterCard) from card response.
     */
    protected fun extractApplicationLabel(data: ByteArray): String? {
        Log.d(TAG, "Extract Application label")
        val labelBytes = TlvUtil.getValue(data, EmvTag.APPLICATION_PREFERRED_NAME)
            ?: TlvUtil.getValue(data, EmvTag.APPLICATION_LABEL)
        return labelBytes?.let { String(it) }
    }

    /**
     * Extracts bank BIC and IBAN from card data.
     */
    protected fun extractBankData(data: ByteArray) {
        TlvUtil.getValue(data, EmvTag.BANK_IDENTIFIER_CODE)?.let {
            template.get()?.card?.bic = String(it)
        }
        TlvUtil.getValue(data, EmvTag.IBAN)?.let {
            template.get()?.card?.iban = String(it)
        }
    }

    /**
     * Extracts cardholder name from card data and parses lastname/firstname.
     */
    protected fun extractCardHolderName(data: ByteArray) {
        TlvUtil.getValue(data, EmvTag.CARDHOLDER_NAME)?.let {
            val nameParts = String(it).trim().split(TrackUtils.CARD_HOLDER_NAME_SEPARATOR)
            if (nameParts.isNotEmpty()) {
                template.get()?.card?.holderLastname = nameParts[0].trim().ifBlank { null }
                if (nameParts.size == 2) {
                    template.get()?.card?.holderFirstname = nameParts[1].trim().ifBlank { null }
                }
            }
        }
    }

    /**
     * Returns the Log Entry tag value (used to locate transaction logs on the card).
     */
    protected fun getLogEntry(selectResponse: ByteArray): ByteArray? {
        return TlvUtil.getValue(selectResponse, EmvTag.LOG_ENTRY, EmvTag.VISA_LOG_ENTRY)
    }

    /**
     * Sends GET DATA command to retrieve transaction counter (ATC).
     */
    @Throws(CommunicationException::class)
    protected fun getTransactionCounter(): Int {
        Log.d(TAG, "Get Transaction Counter ATC")
        val data = template.get()?.provider?.transceive(
            CommandApdu(CommandEnum.GET_DATA, 0x9F, 0x36, 0).toBytes()
        ) ?: return UNKNOWN

        if (ResponseUtils.isSucceed(data)) {
            TlvUtil.getValue(data, EmvTag.APP_TRANSACTION_COUNTER)?.let {
                return BytesUtils.byteArrayToInt(it)
            }
        }
        return UNKNOWN
    }

    /**
     * Sends GET DATA command to get number of remaining PIN tries.
     */
    @Throws(CommunicationException::class)
    protected fun getLeftPinTry(): Int {
        Log.d(TAG, "Get Left PIN try")
        val data = template.get()?.provider?.transceive(
            CommandApdu(CommandEnum.GET_DATA, 0x9F, 0x17, 0).toBytes()
        ) ?: return UNKNOWN

        if (ResponseUtils.isSucceed(data)) {
            TlvUtil.getValue(data, EmvTag.PIN_TRY_COUNTER)?.let {
                return BytesUtils.byteArrayToInt(it)
            }
        }
        return UNKNOWN
    }

    /**
     * Retrieves the transaction log format (list of TagAndLength entries).
     */
    @Throws(CommunicationException::class)
    protected fun getLogFormat(): List<TagAndLength> {
        Log.d(TAG, "GET log format")

        val data = template.get()?.provider?.transceive(
            CommandApdu(CommandEnum.GET_DATA, 0x9F, 0x4F, 0).toBytes()
        )

        return if (data != null && ResponseUtils.isSucceed(data)) {
            TlvUtil.parseTagAndLength(TlvUtil.getValue(data, EmvTag.LOG_FORMAT))
        } else {
            Log.w(TAG, "No Log format found")
            emptyList()
        }
    }

    /**
     * Extracts list of past EMV transactions by reading log entries.
     */
    @Throws(CommunicationException::class)
    protected fun extractLogEntry(logEntry: ByteArray?): List<EmvTransactionRecord> {
        val recordList = mutableListOf<EmvTransactionRecord>()

        if (template.get()?.config?.readTransactions == true && logEntry != null) {
            val format = getLogFormat()
            if (format.isNotEmpty()) {
                val sfi = logEntry[0].toInt()
                val recordCount = logEntry[1].toInt()

                for (rec in 1..recordCount) {
                    val response = template.get()?.provider?.transceive(
                        CommandApdu(CommandEnum.READ_RECORD, rec, (sfi shl 3) or 4, 0).toBytes()
                    ) ?: break

                    if (ResponseUtils.isSucceed(response)) {
                        try {
                            val original = EmvTransactionRecord().apply {
                                parse(response, format)
                            }

                            val amountCorrected = original.amount?.let {
                                if (it >= 1500000000) it - 1500000000 else it
                            }

                            if (amountCorrected == null || amountCorrected <= 1) continue

                            val finalRecord = original.copy(
                                amount = amountCorrected,
                                currency = original.currency ?: CurrencyEnum.XXX
                            )

                            recordList.add(finalRecord)
                        } catch (e: Exception) {
                            Log.e(TAG, "Error in transaction format: ${e.message}", e)
                        }
                    } else {
                        break
                    }
                }
            }
        }

        return recordList
    }
}
