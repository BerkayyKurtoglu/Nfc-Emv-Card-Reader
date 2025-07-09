package com.berkay.nfcemvcardreader.emvreader.iso7816emv.tag

import com.berkay.nfcemvcardreader.emvreader.enums.TagTypeEnum
import com.berkay.nfcemvcardreader.emvreader.enums.TagValueTypeEnum
import com.berkay.nfcemvcardreader.emvreader.utils.bitutils.BytesUtils

/**
 * Creates a TagImpl instance from a raw byte array.
 */
class CustomTag(
    private val idBytes: ByteArray,
    private val tagValueType: TagValueTypeEnum,
    private val name: String,
    private val description: String
) : ITag {

    private val type: TagTypeEnum
    private val tagClass: ITag.Class

    init {
        if (idBytes.isEmpty()) {
            throw IllegalArgumentException("Param id cannot be empty")
        }
        this.type = if (BytesUtils.matchBitByBitIndex(idBytes[0].toInt() and 0xFF, 5)) {
            TagTypeEnum.CONSTRUCTED
        } else {
            TagTypeEnum.PRIMITIVE
        }
        val classValue = (idBytes[0].toInt() ushr 6) and 0x03
        this.tagClass = when (classValue) {
            0x01 -> ITag.Class.APPLICATION
            0x02 -> ITag.Class.CONTEXT_SPECIFIC
            0x03 -> ITag.Class.PRIVATE
            else -> ITag.Class.UNIVERSAL
        }
    }

    override fun isConstructed(): Boolean = type == TagTypeEnum.CONSTRUCTED

    override fun getTagBytes(): ByteArray = idBytes

    override fun getName(): String = name

    override fun getDescription(): String = description

    override fun getTagValueType(): TagValueTypeEnum = tagValueType

    override fun getType(): TagTypeEnum = type

    override fun getTagClass(): ITag.Class = tagClass

    override fun equals(other: Any?): Boolean {
        if (other !is ITag) return false
        return idBytes.contentEquals(other.getTagBytes())
    }

    override fun hashCode(): Int {
        return 59 * 3 + idBytes.contentHashCode()
    }

    override fun getNumTagBytes(): Int = idBytes.size

    override fun toString(): String {
        return buildString {
            append("Tag[")
            append(BytesUtils.bytesToString(idBytes))
            append("] Name=")
            append(name)
            append(", TagType=")
            append(type)
            append(", ValueType=")
            append(tagValueType)
            append(", Class=")
            append(tagClass)
        }
    }
}
