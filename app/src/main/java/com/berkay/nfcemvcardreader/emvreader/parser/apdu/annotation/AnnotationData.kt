package com.berkay.nfcemvcardreader.emvreader.parser.apdu.annotation

import com.berkay.nfcemvcardreader.emvreader.iso7816emv.tag.EmvTag
import com.berkay.nfcemvcardreader.emvreader.iso7816emv.tag.ITag
import com.berkay.nfcemvcardreader.emvreader.utils.bitutils.BytesUtils
import java.lang.reflect.Field

/**
 * Holds metadata extracted from @Data annotations for APDU parsing.
 */
class AnnotationData : Comparable<AnnotationData>, Cloneable {

    var size: Int = 0
    var index: Int = 0
    var readHexa: Boolean = false
    var field: Field? = null
    var dateStandard: Int = 0
    var format: String = ""
    var tag: ITag? = null
    var skip: Boolean = false

    override fun compareTo(other: AnnotationData): Int {
        return index.compareTo(other.index)
    }

    override fun equals(other: Any?): Boolean {
        return other is AnnotationData && index == other.index
    }

    fun initFromAnnotation(data: Data) {
        dateStandard = data.dateStandard
        format = data.format
        index = data.index
        readHexa = data.readHexa
        size = data.size
        if (data.tag.isNotEmpty()) {
            tag = EmvTag.find(BytesUtils.fromString(data.tag))
        }
    }

    public override fun clone(): AnnotationData {
        val copy = AnnotationData()
        copy.dateStandard = dateStandard
        copy.field = field
        copy.format = format
        copy.index = index
        copy.readHexa = readHexa
        copy.size = size
        copy.tag = tag
        return copy
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}
