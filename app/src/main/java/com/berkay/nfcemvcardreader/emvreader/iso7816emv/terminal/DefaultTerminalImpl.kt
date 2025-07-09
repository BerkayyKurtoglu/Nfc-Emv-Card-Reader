package com.berkay.nfcemvcardreader.emvreader.iso7816emv.terminal

import java.security.SecureRandom
import java.text.SimpleDateFormat
import java.util.Date
import com.berkay.nfcemvcardreader.emvreader.iso7816emv.tag.EmvTag
import com.berkay.nfcemvcardreader.emvreader.iso7816emv.TagAndLength
import papara.framework.nfckit.iso7816emv.terminal.ITerminal
import com.berkay.nfcemvcardreader.emvreader.model.enums.CountryCodeEnum
import com.berkay.nfcemvcardreader.emvreader.model.enums.CurrencyEnum
import com.berkay.nfcemvcardreader.emvreader.model.enums.TransactionTypeEnum
import com.berkay.nfcemvcardreader.emvreader.utils.bitutils.BytesUtils

/**
 * Factory to create default terminal implementation.
 */
class DefaultTerminalImpl : ITerminal {

    private val random = SecureRandom()

    private var countryCode: CountryCodeEnum = CountryCodeEnum.TR

    override fun constructValue(tagAndLength: TagAndLength): ByteArray {
        val ret = ByteArray(tagAndLength.length)
        var valBytes: ByteArray? = null

        when (tagAndLength.tag) {
            EmvTag.TERMINAL_TRANSACTION_QUALIFIERS -> {
                val terminalQual = TerminalTransactionQualifiers()
                terminalQual.setContactlessVSDCsupported(true)
                terminalQual.setContactEMVsupported(true)
                terminalQual.setMagneticStripeSupported(true)
                terminalQual.setContactlessEMVmodeSupported(true)
                terminalQual.setOnlinePINsupported(true)
                terminalQual.setReaderIsOfflineOnly(false)
                terminalQual.setSignatureSupported(true)
                terminalQual.setContactChipOfflinePINsupported(true)
                terminalQual.setIssuerUpdateProcessingSupported(true)
                terminalQual.setConsumerDeviceCVMsupported(true)
                valBytes = terminalQual.getBytes()
            }

            EmvTag.TERMINAL_COUNTRY_CODE -> {
                val padded = countryCode.numeric.toString().padStart(tagAndLength.length * 2, '0')
                valBytes = BytesUtils.fromString(padded)
            }

            EmvTag.TRANSACTION_CURRENCY_CODE -> {
                val currencyCode = CurrencyEnum.find(countryCode, CurrencyEnum.EUR).isoCodeNumeric
                val padded = currencyCode.toString().padStart(tagAndLength.length * 2, '0')
                valBytes = BytesUtils.fromString(padded)
            }

            EmvTag.TRANSACTION_DATE -> {
                val sdf = SimpleDateFormat("yyMMdd")
                valBytes = BytesUtils.fromString(sdf.format(Date()))
            }

            EmvTag.TRANSACTION_TYPE, EmvTag.TERMINAL_TRANSACTION_TYPE -> {
                valBytes = byteArrayOf(TransactionTypeEnum.PURCHASE.value.toByte())
            }

            EmvTag.AMOUNT_AUTHORISED_NUMERIC -> {
                valBytes = BytesUtils.fromString("01")
            }

            EmvTag.TERMINAL_TYPE -> {
                valBytes = byteArrayOf(0x22)
            }

            EmvTag.TERMINAL_CAPABILITIES -> {
                valBytes = byteArrayOf(0xE0.toByte(), 0xA0.toByte(), 0x00)
            }

            EmvTag.ADDITIONAL_TERMINAL_CAPABILITIES -> {
                valBytes = byteArrayOf(0x8e.toByte(), 0x00, 0xb0.toByte(), 0x50, 0x05)
            }

            EmvTag.DS_REQUESTED_OPERATOR_ID -> {
                valBytes = BytesUtils.fromString("7A45123EE59C7F40")
            }

            EmvTag.UNPREDICTABLE_NUMBER -> {
                random.nextBytes(ret)
            }

            EmvTag.MERCHANT_TYPE_INDICATOR -> {
                valBytes = byteArrayOf(0x01)
            }

            EmvTag.TERMINAL_TRANSACTION_INFORMATION -> {
                valBytes = byteArrayOf(0xC0.toByte(), 0x80.toByte(), 0x00)
            }
        }

        if (valBytes != null) {
            System.arraycopy(valBytes, 0, ret, maxOf(ret.size - valBytes.size, 0), minOf(valBytes.size, ret.size))
        }

        return ret
    }

    fun setCountryCode(countryCode: CountryCodeEnum?) {
        if (countryCode != null) {
            this.countryCode = countryCode
        }
    }
}
