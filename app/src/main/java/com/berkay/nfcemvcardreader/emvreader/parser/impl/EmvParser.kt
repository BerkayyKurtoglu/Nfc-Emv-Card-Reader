package com.berkay.nfcemvcardreader.emvreader.parser.impl

import android.util.Log
import com.berkay.nfcemvcardreader.emvreader.enums.CommandEnum
import com.berkay.nfcemvcardreader.emvreader.enums.EmvCardScheme
import com.berkay.nfcemvcardreader.emvreader.enums.SwEnum
import com.berkay.nfcemvcardreader.emvreader.exceptions.CommunicationException
import com.berkay.nfcemvcardreader.emvreader.iso7816emv.tag.EmvTag
import com.berkay.nfcemvcardreader.emvreader.model.Afl
import com.berkay.nfcemvcardreader.emvreader.model.Application
import com.berkay.nfcemvcardreader.emvreader.model.EmvCard
import com.berkay.nfcemvcardreader.emvreader.model.enums.ApplicationStepEnum
import com.berkay.nfcemvcardreader.emvreader.model.enums.CardStateEnum
import com.berkay.nfcemvcardreader.emvreader.parser.EmvTemplate
import com.berkay.nfcemvcardreader.emvreader.utils.CommandApdu
import com.berkay.nfcemvcardreader.emvreader.utils.ResponseUtils
import com.berkay.nfcemvcardreader.emvreader.utils.TlvUtil
import com.berkay.nfcemvcardreader.emvreader.utils.TrackUtils
import com.berkay.nfcemvcardreader.emvreader.utils.bitutils.BytesUtils
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.regex.Pattern

private const val TAG = "EmvParser"
private val PATTERN = Pattern.compile(".*")

/**
 * Default EMV parser implementation. Parses AID, GPO, AFL, and log data.
 */
class EmvParser(template: EmvTemplate) : AbstractParser(template) {

    override fun getId(): Pattern = PATTERN

    override fun parse(application: Application): Boolean = extractPublicData(application)

    /**
     * Selects AID and extracts card data and metadata.
     */
    private fun extractPublicData(application: Application): Boolean {
        val aid = application.aid ?: return false
        val data = selectAID(aid)
        if (ResponseUtils.contains(data, SwEnum.SW_9000, SwEnum.SW_6285)) {
            application.readingStep = ApplicationStepEnum.SELECTED
            val success = parse(data, application)
            if (success) {
                val aid = BytesUtils.bytesToStringNoSpace(
                    TlvUtil.getValue(
                        data,
                        EmvTag.DEDICATED_FILE_NAME
                    )
                )
                val label = extractApplicationLabel(data) ?: application.applicationLabel

                Log.d(TAG, "Application label: $label with Aid: $aid")

                val card = template.get()?.card
                card?.scheme = findCardScheme(aid, card?.cardNumber)
                application.aid = BytesUtils.fromString(aid)
                application.applicationLabel = label
                application.leftPinTry = getLeftPinTry()
                application.transactionCounter = getTransactionCounter()
                card?.state = CardStateEnum.ACTIVE
            }
            return success
        }
        return false
    }

    /**
     * Detects the real card scheme based on AID and PAN (e.g. Visa, MasterCard).
     */
    private fun findCardScheme(aid: String, cardNumber: String?): EmvCardScheme? {
        var type = EmvCardScheme.getCardTypeByAid(aid)
        if (type == EmvCardScheme.CB) {
            EmvCardScheme.getCardTypeByCardNumber(cardNumber)?.let {
                type = it
                Log.d(TAG, "Real type: ${it.name}")
            }
        }
        return type
    }

    /**
     * Performs full EMV parsing sequence: GPO, AFL, Track, Logs etc.
     */
    @Throws(CommunicationException::class)
    private fun parse(selectResponse: ByteArray, application: Application): Boolean {
        var gpo = getGetProcessingOptions(TlvUtil.getValue(selectResponse, EmvTag.PDOL))
        extractBankData(selectResponse)
        val logEntry = getLogEntry(selectResponse)

        if (!ResponseUtils.isSucceed(gpo)) {
            gpo = getGetProcessingOptions(null)
            if (!ResponseUtils.isSucceed(gpo)) {
                gpo = template.get()?.provider?.transceive(
                    CommandApdu(CommandEnum.READ_RECORD, 1, 0x0C, 0).toBytes()
                ) ?: return false
                if (!ResponseUtils.isSucceed(gpo)) return false
            }
        }

        application.readingStep = ApplicationStepEnum.READ

        if (extractCommonsCardData(gpo)) {
            application.listTransactions = extractLogEntry(logEntry)
            return true
        }
        return false
    }

    /**
     * Extracts common card data using AFL + Track1/2.
     */
    @Throws(CommunicationException::class)
    private fun extractCommonsCardData(gpo: ByteArray): Boolean {
        var data = TlvUtil.getValue(gpo, EmvTag.RESPONSE_MESSAGE_TEMPLATE_1)
        if (data != null) {
            data = data.copyOfRange(2, data.size)
        } else {
            if (extractTrackData(template.get()?.card ?: return false, gpo)) {
                extractCardHolderName(gpo)
                return true
            }
            data = TlvUtil.getValue(gpo, EmvTag.APPLICATION_FILE_LOCATOR)
        }

        if (data != null) {
            val aflList = extractAfl(data)
            for (afl in aflList) {
                for (index in afl.firstRecord..afl.lastRecord) {
                    val info = template.get()?.provider?.transceive(
                        CommandApdu(
                            CommandEnum.READ_RECORD,
                            index,
                            (afl.sfi shl 3) or 4,
                            0
                        ).toBytes()
                    ) ?: continue
                    if (ResponseUtils.isSucceed(info)) {
                        extractCardHolderName(info)
                        if (extractTrackData(template.get()?.card ?: return false, info)) {
                            return true
                        }
                    }
                }
            }
        }

        return false
    }

    /**
     * Parses AFL (Application File Locator) bytes into AFL entries.
     */
    private fun extractAfl(aflBytes: ByteArray): List<Afl> {
        val list = mutableListOf<Afl>()
        val stream = ByteArrayInputStream(aflBytes)
        while (stream.available() >= 4) {
            val afl = Afl().apply {
                sfi = stream.read() ushr 3
                firstRecord = stream.read()
                lastRecord = stream.read()
                offlineAuthentication = stream.read() == 1
            }
            list.add(afl)
        }
        return list
    }

    /**
     * Sends GPO (Get Processing Options) command based on PDOL.
     */
    @Throws(CommunicationException::class)
    private fun getGetProcessingOptions(pdol: ByteArray?): ByteArray {
        val list = TlvUtil.parseTagAndLength(pdol)
        val out = ByteArrayOutputStream()
        try {
            out.write(EmvTag.COMMAND_TEMPLATE.getTagBytes())
            out.write(TlvUtil.getLength(list))
            list.forEach { tagLen ->
                out.write(template.get()?.terminal?.constructValue(tagLen))
            }
        } catch (e: IOException) {
            Log.e(TAG, "Construct GPO Command: ${e.message}", e)
        }
        return template.get()?.provider?.transceive(
            CommandApdu(CommandEnum.GPO, out.toByteArray(), 0).toBytes()
        ) ?: ByteArray(0)
    }

    /**
     * Extracts Track1 and Track2 data from EMV response.
     */
    private fun extractTrackData(card: EmvCard, data: ByteArray): Boolean {
        card.track1 = TrackUtils.extractTrack1Data(
            TlvUtil.getValue(data, EmvTag.TRACK1_DATA)
        )
        card.track2 = TrackUtils.extractTrack2EquivalentData(
            TlvUtil.getValue(data, EmvTag.TRACK_2_EQV_DATA, EmvTag.TRACK2_DATA)
        )
        return card.track1 != null || card.track2 != null
    }
}
