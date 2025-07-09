package com.berkay.nfcemvcardreader.emvreader.parser

import com.berkay.nfcemvcardreader.emvreader.exceptions.CommunicationException

/**
 * Interface for transmitting commands to the card and receiving responses.
 */
interface IProvider {

    /**
     * Sends a command to the card and returns the response.
     *
     * @param command The command bytes to send.
     * @return The response bytes from the card.
     * @throws CommunicationException if there is a communication error.
     */
    @Throws(CommunicationException::class)
    fun transceive(command: ByteArray): ByteArray?

    /**
     * Returns the card's ATR (Answer To Reset) or ATS (Answer To Select).
     *
     * @return Byte array containing ATR or ATS.
     */
    fun getAt(): ByteArray
}
