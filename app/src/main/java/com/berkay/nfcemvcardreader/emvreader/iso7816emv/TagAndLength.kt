package com.berkay.nfcemvcardreader.emvreader.iso7816emv

import com.berkay.nfcemvcardreader.emvreader.iso7816emv.tag.ITag
import java.util.Arrays

data class TagAndLength(
    val tag: ITag,
    val length: Int
) {
    fun getBytes(): ByteArray {
        val tagBytes = tag.getTagBytes()
        val tagAndLengthBytes = Arrays.copyOf(tagBytes, tagBytes.size + 1)
        tagAndLengthBytes[tagAndLengthBytes.size - 1] = length.toByte()
        return tagAndLengthBytes
    }
}

/*class TagAndLength(
    val tag: ITag,
    val length: Int
) {

    fun getBytes(): ByteArray {
        val tagBytes = tag.getTagBytes()
        val tagAndLengthBytes = Arrays.copyOf(tagBytes, tagBytes.size + 1)
        tagAndLengthBytes[tagAndLengthBytes.size - 1] = length.toByte()
        return tagAndLengthBytes
    }

    override fun toString(): String {
        return "${tag.toString()} length: $length"
    }
}*/
