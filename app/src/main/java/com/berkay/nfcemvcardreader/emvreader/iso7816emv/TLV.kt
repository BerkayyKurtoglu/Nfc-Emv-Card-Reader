package com.berkay.nfcemvcardreader.emvreader.iso7816emv

import com.berkay.nfcemvcardreader.emvreader.iso7816emv.tag.ITag

data class TLV(
    val tag: ITag,
    val length: Int,
    val rawEncodedLengthBytes: ByteArray,
    val valueBytes: ByteArray
) {
    init {
        require(valueBytes.size == length) { "length != bytes.length" }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TLV

        if (length != other.length) return false
        if (tag != other.tag) return false
        if (!rawEncodedLengthBytes.contentEquals(other.rawEncodedLengthBytes)) return false
        if (!valueBytes.contentEquals(other.valueBytes)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = length
        result = 31 * result + tag.hashCode()
        result = 31 * result + rawEncodedLengthBytes.contentHashCode()
        result = 31 * result + valueBytes.contentHashCode()
        return result
    }
}
