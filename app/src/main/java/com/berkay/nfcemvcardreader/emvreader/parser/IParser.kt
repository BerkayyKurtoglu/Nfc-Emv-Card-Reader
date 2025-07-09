package com.berkay.nfcemvcardreader.emvreader.parser

import com.berkay.nfcemvcardreader.emvreader.exceptions.CommunicationException
import com.berkay.nfcemvcardreader.emvreader.model.Application
import java.util.regex.Pattern

/**
 * Parser interface to handle card parsing logic.
 */
interface IParser {

    /**
     * Returns the RID or AID pattern of the application to parse.
     */
    fun getId(): Pattern

    /**
     * Parses the card using the given application.
     *
     * @param application Current application instance.
     * @return true if parsing was successful without errors.
     * @throws CommunicationException if a communication error occurs.
     */
    @Throws(CommunicationException::class)
    fun parse(application: Application): Boolean
}
