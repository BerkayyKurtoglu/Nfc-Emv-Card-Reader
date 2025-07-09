package com.berkay.nfcemvcardreader.emvreader.utils

import net.sf.scuba.tlv.TLVInputStream
import net.sf.scuba.tlv.TLVUtil
import android.util.Log
import com.berkay.nfcemvcardreader.emvreader.enums.SwEnum
import com.berkay.nfcemvcardreader.emvreader.enums.TagValueTypeEnum
import com.berkay.nfcemvcardreader.emvreader.exceptions.TlvException
import com.berkay.nfcemvcardreader.emvreader.iso7816emv.tag.EmvTag
import com.berkay.nfcemvcardreader.emvreader.iso7816emv.tag.ITag
import com.berkay.nfcemvcardreader.emvreader.iso7816emv.TLV
import com.berkay.nfcemvcardreader.emvreader.iso7816emv.TagAndLength
import com.berkay.nfcemvcardreader.emvreader.utils.bitutils.BytesUtils
import java.io.ByteArrayInputStream
import java.io.EOFException
import java.io.IOException

/**
 * Utility methods for working with TLV (Tag-Length-Value) structures.
 */
object TlvUtil {

    private const val TAG = "TlvUtil"

    private fun searchTagById(tagId: Int): ITag {
        return EmvTag.getNotNull(TLVUtil.getTagAsBytes(tagId))
    }

    private fun getFormattedTagAndLength(data: ByteArray, indentLength: Int): String {
        val indent = getSpaces(indentLength)
        val result = buildString {
            TLVInputStream(ByteArrayInputStream(data)).use { stream ->
                var firstLine = true
                while (stream.available() > 0) {
                    if (!firstLine) append("\n")
                    firstLine = false
                    append(indent)
                    val tag = searchTagById(stream.readTag())
                    val length = stream.readLength()
                    append(prettyPrintHex(tag.getTagBytes()))
                    append(" ")
                    append(String.format("%02x", length))
                    append(" -- ")
                    append(tag.getName())
                }
            }
        }
        return result
    }

    private fun getNextTLV(stream: TLVInputStream): TLV? {
        return try {
            if (stream.available() <= 2) return null
            val tag = searchTagById(stream.readTag())
            val length = stream.readLength()
            if (stream.available() >= length) {
                TLV(tag, length, TLVUtil.getLengthAsBytes(length), stream.readValue())
            } else null
        } catch (eof: EOFException) {
            Log.d(TAG, eof.message ?: "EOFException", eof)
            null
        } catch (e: IOException) {
            Log.e(TAG, e.message ?: "IOException", e)
            null
        }
    }

    private fun getTagValueAsString(tag: ITag, value: ByteArray): String {
        return when (tag.getTagValueType()) {
            TagValueTypeEnum.TEXT -> "=${String(value)}"
            TagValueTypeEnum.NUMERIC -> "NUMERIC"
            TagValueTypeEnum.BINARY -> "BINARY"
            TagValueTypeEnum.MIXED -> "=" + getSafePrintChars(value)
            TagValueTypeEnum.DOL -> ""
            else -> ""
        }
    }

    fun parseTagAndLength(data: ByteArray?): List<TagAndLength> {
        val result = mutableListOf<TagAndLength>()
        if (data != null) {
            TLVInputStream(ByteArrayInputStream(data)).use { stream ->
                while (stream.available() > 0) {
                    if (stream.available() < 2) {
                        throw TlvException("Data length < 2 : ${stream.available()}")
                    }
                    val tag = searchTagById(stream.readTag())
                    val length = stream.readLength()
                    result.add(TagAndLength(tag, length))
                }
            }
        }
        return result
    }

    fun prettyPrintAPDUResponse(data: ByteArray): String = prettyPrintAPDUResponse(data, 0)

    fun getListTLV(data: ByteArray, tag: ITag, add: Boolean): List<TLV> {
        val result = mutableListOf<TLV>()
        TLVInputStream(ByteArrayInputStream(data)).use { stream ->
            while (stream.available() > 0) {
                val tlv = getNextTLV(stream) ?: break
                if (add) {
                    result.add(tlv)
                } else if (tlv.tag.isConstructed()) {
                    result.addAll(getListTLV(tlv.valueBytes, tag, tlv.tag == tag))
                }
            }
        }
        return result
    }

    fun getListTLV(data: ByteArray?, vararg tags: ITag): List<TLV> {
        val result = mutableListOf<TLV>()
        TLVInputStream(ByteArrayInputStream(data)).use { stream ->
            while (stream.available() > 0) {
                val tlv = getNextTLV(stream) ?: break
                if (tags.contains(tlv.tag)) {
                    result.add(tlv)
                } else if (tlv.tag.isConstructed()) {
                    result.addAll(getListTLV(tlv.valueBytes, *tags))
                }
            }
        }
        return result
    }

    fun getValue(data: ByteArray?, vararg tags: ITag): ByteArray? {
        if (data == null) return null
        TLVInputStream(ByteArrayInputStream(data)).use { stream ->
            while (stream.available() > 0) {
                val tlv = getNextTLV(stream) ?: break
                if (tags.contains(tlv.tag)) {
                    return tlv.valueBytes
                } else if (tlv.tag.isConstructed()) {
                    val nested = getValue(tlv.valueBytes, *tags)
                    if (nested != null) return nested
                }
            }
        }
        return null
    }

    private fun prettyPrintAPDUResponse(data: ByteArray, indentLength: Int): String {
        val result = StringBuilder()
        TLVInputStream(ByteArrayInputStream(data)).use { stream ->
            while (stream.available() > 0) {
                result.append("\n")
                if (stream.available() == 2) {
                    stream.mark(0)
                    val value = ByteArray(2)
                    try {
                        stream.read(value)
                    } catch (_: IOException) {}
                    val sw = SwEnum.getSW(value)
                    if (sw != null) {
                        result.append(getSpaces(0))
                        result.append(BytesUtils.bytesToString(value)).append(" -- ")
                        result.append(sw.detail)
                        continue
                    }
                    stream.reset()
                }

                result.append(getSpaces(indentLength))
                val tlv = getNextTLV(stream) ?: run {
                    result.setLength(0)
                    Log.d(TAG, "TLV format error")
                    return result.toString()
                }

                val tagBytes = tlv.tag.getTagBytes()
                val lengthBytes = tlv.rawEncodedLengthBytes
                val valueBytes = tlv.valueBytes
                val tag = tlv.tag

                result.append(prettyPrintHex(tagBytes)).append(" ")
                result.append(prettyPrintHex(lengthBytes)).append(" -- ")
                result.append(tag.getName())

                val extraIndent = (tagBytes.size + lengthBytes.size) * 3

                if (tag.isConstructed()) {
                    result.append(prettyPrintAPDUResponse(valueBytes, indentLength + extraIndent))
                } else {
                    result.append("\n")
                    if (tag.getTagValueType() == TagValueTypeEnum.DOL) {
                        result.append(getFormattedTagAndLength(valueBytes, indentLength + extraIndent))
                    } else {
                        result.append(getSpaces(indentLength + extraIndent))
                        result.append(
                            prettyPrintHex(
                                BytesUtils.bytesToStringNoSpace(valueBytes),
                                indentLength + extraIndent
                            )
                        )
                        result.append(" (").append(getTagValueAsString(tag, valueBytes)).append(")")
                    }
                }
            }
        }
        return result.toString()
    }

    private fun getSpaces(length: Int): String {
        return " ".repeat(length)
    }

    private fun prettyPrintHex(data: ByteArray): String {
        return prettyPrintHex(BytesUtils.bytesToStringNoSpace(data), 0, true)
    }

    private fun prettyPrintHex(input: String, indent: Int): String {
        return prettyPrintHex(input, indent, true)
    }

    private fun prettyPrintHex(input: String, indent: Int, wrapLines: Boolean): String {
        return buildString {
            for (i in input.indices) {
                append(input[i])
                val next = i + 1
                when {
                    wrapLines && next % 32 == 0 && next != input.length -> {
                        append("\n").append(getSpaces(indent))
                    }
                    next % 2 == 0 && next != input.length -> append(" ")
                }
            }
        }
    }

    private fun getSafePrintChars(byteArray: ByteArray?): String {
        return getSafePrintChars(byteArray, 0, byteArray?.size ?: 0)
    }

    private fun getSafePrintChars(byteArray: ByteArray?, startPos: Int, length: Int): String {
        if (byteArray == null) return ""
        if (byteArray.size < startPos + length) {
            throw IllegalArgumentException("startPos($startPos)+length($length) > byteArray.length(${byteArray.size})")
        }
        return buildString {
            for (i in startPos until startPos + length) {
                append(if (byteArray[i] in 0x20..0x7E) byteArray[i].toInt().toChar() else '.')
            }
        }
    }

    fun getLength(list: List<TagAndLength>?): Int {
        return list?.sumOf { it.length } ?: 0
    }
}
