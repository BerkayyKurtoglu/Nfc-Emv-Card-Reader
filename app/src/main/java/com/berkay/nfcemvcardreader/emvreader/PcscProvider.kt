package com.berkay.nfcemvcardreader.emvreader

import android.nfc.tech.IsoDep
import com.berkay.nfcemvcardreader.emvreader.exceptions.CommunicationException
import com.berkay.nfcemvcardreader.emvreader.parser.IProvider
import java.io.IOException

/**
 * PC/SC (Personal Computer/Smart Card) provider implementation using Android's IsoDep interface.
 *
 * This class handles communication with EMV-compatible smart cards over NFC, conforming to the IProvider interface.
 * It uses the IsoDep protocol to transmit APDU commands and retrieve responses from the card.
 *
 * @see IsoDep
 * @see IProvider
 */
class PcscProvider : IProvider {

    private lateinit var tagCommunicator: IsoDep

    override fun transceive(command: ByteArray): ByteArray? {
        var response: ByteArray? = null
        try {
            // send command to emv card
            if (tagCommunicator.isConnected) {
                response = tagCommunicator.transceive(command)
            }
        } catch (e: IOException) {
            throw CommunicationException(e.message ?: "Error")
        }
        return response
    }


    override fun getAt(): ByteArray {
        var result: ByteArray?
        result = tagCommunicator.historicalBytes // for tags using NFC-B
        if (result == null) {
            result = tagCommunicator.hiLayerResponse // for tags using NFC-B
        }
        return result
    }

    fun setTagCommunicator(tagIsoDep: IsoDep) {
        this.tagCommunicator = tagIsoDep
    }
}
