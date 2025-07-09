package com.berkay.nfcemvcardreader.emvreader.parser.impl

import com.berkay.nfcemvcardreader.emvreader.enums.CommandEnum
import com.berkay.nfcemvcardreader.emvreader.enums.SwEnum
import com.berkay.nfcemvcardreader.emvreader.exceptions.CommunicationException
import com.berkay.nfcemvcardreader.emvreader.parser.IProvider
import com.berkay.nfcemvcardreader.emvreader.utils.CommandApdu
import com.berkay.nfcemvcardreader.emvreader.utils.ResponseUtils

/**
 * Wrapper class for IProvider. Handles special SW_6C and SW_61 status words.
 */
class ProviderWrapper(
    private val provider: IProvider,
) : IProvider {

    @Throws(CommunicationException::class)
    override fun transceive(command: ByteArray): ByteArray? {
        var result = provider.transceive(command) ?: return null

        if (ResponseUtils.isEquals(result, SwEnum.SW_6C)) {
            val lastByte = result.lastOrNull() ?: return null
            command[command.lastIndex] = lastByte
            result = provider.transceive(command) ?: return null

        } else if (ResponseUtils.isEquals(result, SwEnum.SW_61)) {
            val le = result.lastOrNull()?.toInt() ?: return null
            val getResponse = CommandApdu(CommandEnum.GET_RESPONSE, null, le).toBytes()
            val temp = provider.transceive(getResponse) ?: return null
            result = temp
        }

        return result
    }

    override fun getAt(): ByteArray {
        return provider.getAt()
    }
}
