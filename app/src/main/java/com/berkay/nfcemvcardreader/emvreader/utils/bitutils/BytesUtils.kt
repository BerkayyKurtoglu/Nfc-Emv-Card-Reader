package com.berkay.nfcemvcardreader.emvreader.utils.bitutils

import java.math.BigInteger

/**
 * Utility object to convert between ByteArray, Int, and formatted Strings.
 */
object BytesUtils {

    private const val MAX_BIT_INTEGER = 31
    private const val HEXA = 16
    private const val LEFT_MASK = 0xF0
    private const val RIGHT_MASK = 0x0F
    private const val CHAR_DIGIT_ZERO = 0x30
    private const val CHAR_DIGIT_SEVEN = 0x37
    private const val CHAR_SPACE = ' '

    fun byteArrayToInt(byteArray: ByteArray): Int {
        return byteArrayToInt(byteArray, 0, byteArray.size)
    }

    fun byteArrayToInt(byteArray: ByteArray, startPos: Int, length: Int): Int {
        requireNotNull(byteArray) { "Parameter 'byteArray' cannot be null" }
        require(length in 1..4) { "Length must be between 1 and 4. Length = $length" }
        require(startPos >= 0 && byteArray.size >= startPos + length) {
            "Length or startPos not valid"
        }

        var value = 0
        for (i in 0 until length) {
            value += (byteArray[startPos + i].toInt() and 0xFF) shl (8 * (length - i - 1))
        }
        return value
    }

    fun bytesToString(bytes: ByteArray): String = formatByte(bytes, space = true, truncate = false)

    fun bytesToString(bytes: ByteArray, truncate: Boolean): String =
        formatByte(bytes, space = true, truncate = truncate)

    fun bytesToStringNoSpace(byte: Byte): String =
        formatByte(byteArrayOf(byte), space = false, truncate = false)

    fun bytesToStringNoSpace(bytes: ByteArray?): String =
        if (bytes == null) "" else formatByte(bytes, space = false, truncate = false)

    fun bytesToStringNoSpace(bytes: ByteArray, truncate: Boolean): String =
        formatByte(bytes, space = false, truncate = truncate)

    private fun formatByte(bytes: ByteArray?, space: Boolean, truncate: Boolean): String {
        if (bytes == null) return ""

        var i = 0
        if (truncate) {
            while (i < bytes.size && bytes[i] == 0.toByte()) i++
        }

        if (i >= bytes.size) return ""

        val sizeMultiplier = if (space) 3 else 2
        val c = CharArray((bytes.size - i) * sizeMultiplier)

        var j = 0
        while (i < bytes.size) {
            var b = ((bytes[i].toInt() and LEFT_MASK) shr 4).toByte()
            c[j++] = (if (b > 9) b + CHAR_DIGIT_SEVEN else b + CHAR_DIGIT_ZERO).toInt().toChar()
            b = (bytes[i].toInt() and RIGHT_MASK).toByte()
            c[j++] = (if (b > 9) b + CHAR_DIGIT_SEVEN else b + CHAR_DIGIT_ZERO).toInt().toChar()
            if (space) c[j++] = CHAR_SPACE
            i++
        }

        return if (space) String(c, 0, c.size - 1) else String(c)
    }

    fun fromString(data: String): ByteArray {
        requireNotNull(data) { "Argument can't be null" }

        val sb = StringBuilder()
        for (c in data) {
            if (!c.isWhitespace()) sb.append(c)
        }

        require(sb.length % 2 == 0) { "Hex binary needs to be even-length: $data" }

        val result = ByteArray(sb.length / 2)
        var j = 0
        for (i in sb.indices step 2) {
            val high = Character.digit(sb[i], 16)
            val low = Character.digit(sb[i + 1], 16)
            result[j++] = ((high shl 4) + low).toByte()
        }
        return result
    }

    fun matchBitByBitIndex(value: Int, bitIndex: Int): Boolean {
        require(bitIndex in 0..MAX_BIT_INTEGER) {
            "parameter 'bitIndex' must be between 0 and 31. bitIndex=$bitIndex"
        }
        return (value and (1 shl bitIndex)) != 0
    }

    fun setBit(data: Byte, bitIndex: Int, on: Boolean): Byte {
        require(bitIndex in 0..7) {
            "parameter 'bitIndex' must be between 0 and 7. bitIndex=$bitIndex"
        }
        return if (on) {
            (data.toInt() or (1 shl bitIndex)).toByte()
        } else {
            (data.toInt() and (1 shl bitIndex).inv()).toByte()
        }
    }

    fun toBinary(bytes: ByteArray): String? {
        if (bytes.isEmpty()) return null
        val hex = bytesToStringNoSpace(bytes)
        val valInt = BigInteger(hex, HEXA)
        val build = StringBuilder(valInt.toString(2))
        val totalBits = bytes.size * 8 // BitUtils.BYTE_SIZE yerine 8 yazıldı
        while (build.length < totalBits) {
            build.insert(0, '0')
        }
        return build.toString()
    }

    fun toByteArray(value: Int): ByteArray {
        return byteArrayOf(
            (value shr 24).toByte(),
            (value shr 16).toByte(),
            (value shr 8).toByte(),
            value.toByte()
        )
    }
}
