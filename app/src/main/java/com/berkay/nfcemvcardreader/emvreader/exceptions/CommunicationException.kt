package com.berkay.nfcemvcardreader.emvreader.exceptions

import java.io.IOException

/**
 * Thrown when an error occurs during communication with an EMV card.
 */
class CommunicationException : IOException {

    constructor() : super()

    constructor(message: String) : super(message)
}
