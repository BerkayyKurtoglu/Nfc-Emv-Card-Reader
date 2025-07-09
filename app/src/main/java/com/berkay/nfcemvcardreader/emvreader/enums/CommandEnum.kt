package com.berkay.nfcemvcardreader.emvreader.enums

/**
 * Represents standard EMV APDU commands used in smart card communication.
 */
enum class CommandEnum(
    val cla: Int,
    val ins: Int,
    val p1: Int,
    val p2: Int
) {
    /**
     * Selects an application or file on the smart card.
     */
    SELECT(0x00, 0xA4, 0x04, 0x00),

    /**
     * Reads a record from the currently selected file.
     */
    READ_RECORD(0x00, 0xB2, 0x00, 0x00),

    /**
     * Sends the Get Processing Options command.
     */
    GPO(0x80, 0xA8, 0x00, 0x00),

    /**
     * Retrieves specific data from the card.
     */
    GET_DATA(0x80, 0xCA, 0x00, 0x00),

    /**
     * Requests remaining data from a previous command.
     */
    GET_RESPONSE(0x00, 0x0C, 0x00, 0x00)
}
