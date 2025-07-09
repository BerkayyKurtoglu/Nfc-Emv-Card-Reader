package com.berkay.nfcemvcardreader.emvreader.parser.apdu.impl

import com.berkay.nfcemvcardreader.emvreader.model.enums.IKeyEnum
import com.berkay.nfcemvcardreader.emvreader.parser.apdu.annotation.AnnotationData
import com.berkay.nfcemvcardreader.emvreader.utils.EnumUtils
import com.berkay.nfcemvcardreader.emvreader.utils.bitutils.BitUtils
import java.util.Calendar
import java.util.Date

/**
 * Factory class to convert byte-level EMV data into high-level Kotlin/Java objects.
 */
object DataFactory {

    const val BCD_DATE = 1
    const val CPCL_DATE = 2

    const val HALF_BYTE_SIZE = 4

    const val BCD_FORMAT = "BCD_Format"

    /**
     * Converts byte data into a Date object depending on format type.
     */
    private fun getDate(annotation: AnnotationData, bit: BitUtils): Date? {
        return when (annotation.dateStandard) {
            BCD_DATE -> bit.getNextDate(annotation.size, annotation.format, true)
            CPCL_DATE -> calculateCplcDate(bit.getNextByte(annotation.size))
            else -> bit.getNextDate(annotation.size, annotation.format)
        }
    }

    /**
     * Parses a CPLC-style date from 2 bytes.
     * Returns null if bytes are both zero.
     */
    fun calculateCplcDate(dateBytes: ByteArray?): Date? {
        if (dateBytes == null || dateBytes.size != 2) {
            throw IllegalArgumentException("Error! CLCP Date values consist always of exactly 2 bytes")
        }

        if (dateBytes[0] == 0.toByte() && dateBytes[1] == 0.toByte()) {
            return null
        }

        val now = Calendar.getInstance()
        val currentYear = now.get(Calendar.YEAR)
        val startOfDecade = currentYear - (currentYear % 10)

        val days = 100 * (dateBytes[0].toInt() and 0xF) +
                10 * ((dateBytes[1].toInt() ushr 4) and 0xF) +
                (dateBytes[1].toInt() and 0xF)

        if (days > 366) {
            throw IllegalArgumentException("Invalid date (or are we parsing it wrong??)")
        }

        val calculatedDate = Calendar.getInstance()
        var year = startOfDecade + ((dateBytes[0].toInt() ushr 4) and 0xF)

        do {
            calculatedDate.clear()
            calculatedDate.set(Calendar.YEAR, year)
            calculatedDate.set(Calendar.DAY_OF_YEAR, days)
            year -= 10
        } while (calculatedDate.after(now))

        return calculatedDate.time
    }

    /**
     * Reads an integer value from bits.
     */
    private fun getInteger(annotation: AnnotationData, bit: BitUtils): Int {
        return bit.getNextInteger(annotation.size)
    }

    /**
     * Main entry point: resolves a value based on the target field type.
     */
    fun getObject(annotation: AnnotationData, bit: BitUtils): Any? {
        val field = annotation.field ?: return null

        return when (val clazz = field.type) {
            Integer::class.java, java.lang.Integer::class.java -> getInteger(annotation, bit)
            Float::class.java, java.lang.Float::class.java -> getFloat(annotation, bit)
            String::class.java -> getString(annotation, bit)
            Date::class.java -> getDate(annotation, bit)
            else -> if (clazz.isEnum) getEnum(annotation, bit) else null
        }
    }

    /**
     * Parses a float value from BCD or integer.
     */
    private fun getFloat(annotation: AnnotationData, bit: BitUtils): Float {
        return if (BCD_FORMAT == annotation.format) {
            bit.getNextHexaString(annotation.size).toFloat()
        } else {
            getInteger(annotation, bit).toFloat()
        }
    }

    /**
     * Parses an enum constant using the annotation value as key.
     */
    /*@Suppress("UNCHECKED_CAST")
    private fun getEnum(annotation: AnnotationData, bit: BitUtils): IKeyEnum? {
        val value = try {
            Integer.parseInt(
                bit.getNextHexaString(annotation.size),
                if (annotation.readHexa) 16 else 10
            )
        } catch (_: NumberFormatException) {
            0
        }

        @Suppress("UNCHECKED_CAST")
        val enumClass = annotation.field?.type as Class<out IKeyEnum>
        return EnumUtils.getValue(value, annotation.field?.type as Class<out IKeyEnum>)
    }*/

    @Suppress("UNCHECKED_CAST")
    private fun getEnum(annotation: AnnotationData, bit: BitUtils): IKeyEnum? {
        var value = 0
        try {
            val radix = if (annotation.readHexa) 16 else 10
            value = bit.getNextHexaString(annotation.size).toInt(radix)
        } catch (e: NumberFormatException) {
            // ignore invalid number
        }
        val enumClass = annotation.field?.type as? Class<out IKeyEnum> ?: return null
        return EnumUtils.getValue(value, enumClass)
    }

    /**
     * Extracts a string from bits, either as hex or ASCII.
     */
    private fun getString(annotation: AnnotationData, bit: BitUtils): String {
        return if (annotation.readHexa) {
            bit.getNextHexaString(annotation.size)
        } else {
            bit.getNextString(annotation.size).trim()
        }
    }
}
