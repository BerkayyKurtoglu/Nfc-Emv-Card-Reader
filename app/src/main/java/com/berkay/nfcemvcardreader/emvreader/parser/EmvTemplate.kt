package com.berkay.nfcemvcardreader.emvreader.parser

import android.util.Log
import com.berkay.nfcemvcardreader.emvreader.enums.CommandEnum
import com.berkay.nfcemvcardreader.emvreader.enums.EmvCardScheme
import com.berkay.nfcemvcardreader.emvreader.exceptions.CommunicationException
import com.berkay.nfcemvcardreader.emvreader.iso7816emv.tag.EmvTag
import papara.framework.nfckit.iso7816emv.terminal.ITerminal
import com.berkay.nfcemvcardreader.emvreader.iso7816emv.terminal.DefaultTerminalImpl
import com.berkay.nfcemvcardreader.emvreader.model.Application
import com.berkay.nfcemvcardreader.emvreader.model.EmvCard
import com.berkay.nfcemvcardreader.emvreader.model.enums.CardStateEnum
import com.berkay.nfcemvcardreader.emvreader.parser.impl.EmvParser
import com.berkay.nfcemvcardreader.emvreader.parser.impl.ProviderWrapper
import com.berkay.nfcemvcardreader.emvreader.utils.AtrUtils
import com.berkay.nfcemvcardreader.emvreader.utils.CPLCUtils
import com.berkay.nfcemvcardreader.emvreader.utils.CommandApdu
import com.berkay.nfcemvcardreader.emvreader.utils.ResponseUtils
import com.berkay.nfcemvcardreader.emvreader.utils.TlvUtil
import com.berkay.nfcemvcardreader.emvreader.utils.bitutils.BytesUtils

/**
 * Detects the EMV card type and applies the correct parser.
 */
class EmvTemplate private constructor(
    var provider: IProvider,
    val terminal: ITerminal,
    val config: Config
) {

    companion object {
        private const val TAG = "EmvTemplate"
        const val MAX_RECORD_SFI = 16
        private val PPSE = "2PAY.SYS.DDF01".toByteArray()
        private val PSE = "1PAY.SYS.DDF01".toByteArray()

        fun builder(): Builder = Builder()
        fun config(): Config = Config()
    }

    private val parsers: MutableList<IParser> = mutableListOf()
    val card: EmvCard = EmvCard()

    init {
        val wrappedProvider = ProviderWrapper(provider)
        if (!config.removeDefaultParsers) {
            addDefaultParsers()
        }
        this.provider = wrappedProvider
    }

    class Config {
        var contactLess: Boolean = true
        var readTransactions: Boolean = true
        var readAllAids: Boolean = true
        var readAt: Boolean = true
        var readCplc: Boolean = false
        var removeDefaultParsers: Boolean = false

        fun setContactLess(value: Boolean) = apply { contactLess = value }
        fun setReadTransactions(value: Boolean) = apply { readTransactions = value }
        fun setReadAllAids(value: Boolean) = apply { readAllAids = value }
        fun setReadAt(value: Boolean) = apply { readAt = value }
        fun setReadCplc(value: Boolean) = apply { readCplc = value }
        fun setRemoveDefaultParsers(value: Boolean) = apply { removeDefaultParsers = value }
    }

    class Builder {
        private var provider: IProvider? = null
        private var terminal: ITerminal? = null
        private var config: Config? = null

        fun setProvider(provider: IProvider) = apply { this.provider = provider }
        fun setTerminal(terminal: ITerminal) = apply { this.terminal = terminal }
        fun setConfig(config: Config) = apply { this.config = config }

        fun build(): EmvTemplate {
            requireNotNull(provider) { "Provider may not be null." }
            val term = terminal ?: DefaultTerminalImpl()
            val conf = config ?: Config()
            return EmvTemplate(provider!!, term, conf)
        }
    }

    fun addParsers(vararg customParsers: IParser): EmvTemplate {
        parsers.addAll(0, customParsers.toList())
        return this
    }

    @Throws(CommunicationException::class)
    fun readEmvCard(): EmvCard {
        if (config.readCplc) {
            readCPLCInfos()
        }

        if (config.readAt) {
            card.atrAts = BytesUtils.bytesToStringNoSpace(provider.getAt())
            card.atrAtsDescription = if (config.contactLess) {
                AtrUtils.getDescriptionFromAts(card.atrAts)
            } else {
                AtrUtils.getDescription(card.atrAts)
            }
        }

        if (!readWithPSE()) {
            readWithAID()
        }

        return card
    }

    @Throws(CommunicationException::class)
    private fun readCPLCInfos() {
        val response = provider.transceive(CommandApdu(CommandEnum.GET_DATA, 0x9F, 0x7F, null, 0).toBytes())
        card.cplc = CPLCUtils.parse(response)
    }

    @Throws(CommunicationException::class)
    private fun readWithPSE(): Boolean {
        Log.d(TAG, "Try to read card with Payment System Environment")

        val data = selectPaymentEnvironment()
        if (ResponseUtils.isSucceed(data)) {
            card.applications.addAll(parseFCIProprietaryTemplate(data))
            card.applications.sort()

            for (app in card.applications) {
                val aidString = BytesUtils.bytesToStringNoSpace(app.aid)
                val matchedParser = parsers.firstOrNull {
                    it.getId().matcher(aidString).matches()
                }
                val parsed = matchedParser?.parse(app) ?: false
                if (parsed) {
                    if (!config.readAllAids) break
                }
            }

            if (card.applications.none { app -> parsers.any {
                    it.getId().matcher(BytesUtils.bytesToStringNoSpace(app.aid))
                        .matches()
                } }) {
                card.state = CardStateEnum.LOCKED
            }

            return true
        } else {
            Log.d(TAG, "${if (config.contactLess) "PPSE" else "PSE"} not found -> Use known AID")
        }

        return false
    }

    private fun addDefaultParsers() {
        //parsers.add(GeldKarteParser(this))
        parsers.add(EmvParser(this))
    }

    @Throws(CommunicationException::class)
    private fun parseFCIProprietaryTemplate(data: ByteArray?): List<Application> {
        val sfiBytes = TlvUtil.getValue(data, EmvTag.SFI)
        return if (sfiBytes != null) {
            val sfi = BytesUtils.byteArrayToInt(sfiBytes)
            Log.d(TAG, "SFI found: $sfi")
            (0 until MAX_RECORD_SFI).mapNotNull { rec ->
                val response = provider.transceive(
                    CommandApdu(CommandEnum.READ_RECORD, rec, (sfi shl 3) or 4, 0).toBytes()
                )
                if (ResponseUtils.isSucceed(response)) {
                    getApplicationTemplate(response)
                } else null
            }.flatten()
        } else {
            Log.d(TAG, "(FCI) Issuer Discretionary Data is already present")
            getApplicationTemplate(data)
        }
    }

    private fun getApplicationTemplate(data: ByteArray?): List<Application> {
        val result = mutableListOf<Application>()
        val templates = TlvUtil.getListTLV(data, EmvTag.APPLICATION_TEMPLATE)
        for (tlv in templates) {
            var aid: ByteArray? = null
            var applicationLabel: String? = null
            var priority = -1

            val innerTags = TlvUtil.getListTLV(
                tlv.valueBytes,
                EmvTag.AID_CARD,
                EmvTag.APPLICATION_LABEL,
                EmvTag.APPLICATION_PRIORITY_INDICATOR
            )

            for (t in innerTags) {
                when (t.tag) {
                    EmvTag.APPLICATION_PRIORITY_INDICATOR -> priority = BytesUtils.byteArrayToInt(t.valueBytes)
                    EmvTag.APPLICATION_LABEL -> applicationLabel = String(t.valueBytes)
                    EmvTag.AID_CARD -> aid = t.valueBytes
                }
            }

            if (aid != null) {
                result.add(
                    Application(
                        aid = aid,
                        applicationLabel = applicationLabel,
                        priority = priority
                    )
                )
            }
        }
        return result
    }

    @Throws(CommunicationException::class)
    private fun readWithAID() {
        Log.d(TAG, "Try to read card with AID")

        for (scheme in EmvCardScheme.entries) {
            for (aid in scheme.aidBytes) {
                val app = Application(
                    aid = aid,
                    applicationLabel = scheme.name
                )

                val aidStr = BytesUtils.bytesToStringNoSpace(aid)
                val parser = parsers.firstOrNull {
                    it.getId().matcher(aidStr).matches()
                }

                if (parser?.parse(app) == true) {
                    card.applications.clear()
                    card.applications.add(app)
                    return
                }
            }
        }
    }

    @Throws(CommunicationException::class)
    private fun selectPaymentEnvironment(): ByteArray? {
        val dir = if (config.contactLess) PPSE else PSE
        Log.d(TAG, "Select ${if (config.contactLess) "PPSE" else "PSE"} Application")
        return provider.transceive(CommandApdu(CommandEnum.SELECT, dir, 0).toBytes())
    }

    fun getParsers(): List<IParser> = parsers.toList()
}
