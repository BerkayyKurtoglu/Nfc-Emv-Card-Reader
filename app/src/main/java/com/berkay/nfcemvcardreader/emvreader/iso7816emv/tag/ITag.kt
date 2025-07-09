package com.berkay.nfcemvcardreader.emvreader.iso7816emv.tag

import com.berkay.nfcemvcardreader.emvreader.enums.TagTypeEnum
import com.berkay.nfcemvcardreader.emvreader.enums.TagValueTypeEnum

interface ITag {

    enum class Class {
        UNIVERSAL, APPLICATION, CONTEXT_SPECIFIC, PRIVATE
    }

    fun isConstructed(): Boolean

    fun getTagBytes(): ByteArray

    fun getName(): String

    fun getDescription(): String

    fun getType(): TagTypeEnum

    fun getTagValueType(): TagValueTypeEnum

    fun getTagClass(): Class

    fun getNumTagBytes(): Int
}
