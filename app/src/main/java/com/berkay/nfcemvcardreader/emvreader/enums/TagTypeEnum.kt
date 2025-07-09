package com.berkay.nfcemvcardreader.emvreader.enums

/**
 * Represents the type of a TLV tag as defined by the EMV specification.
 */
enum class TagTypeEnum {
    /**
     * Tag contains data needed for decoding or processing.
     */
    CONSTRUCTED,

    /**
     * Tag contains only raw or primitive data.
     */
    PRIMITIVE
}
