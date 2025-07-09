package com.berkay.nfcemvcardreader.emvreader.utils.bitutils

import android.util.Log
import java.nio.ByteBuffer
import java.nio.charset.Charset
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Arrays
import java.util.Date
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

class BitUtils {

    companion object {
        private const val TAG = "BitUtils"

        const val BYTE_SIZE = Byte.SIZE_BITS
        const val BYTE_SIZE_F = BYTE_SIZE.toFloat()
        private const val DEFAULT_VALUE = 0xFF
        private val DEFAULT_CHARSET: Charset = Charset.forName("ASCII")
        const val DATE_FORMAT = "yyyyMMdd"
    }

    private val byteTab: ByteArray
    private var currentBitIndex: Int = 0
    private val size: Int

    constructor(data: ByteArray) {
        byteTab = ByteArray(data.size)
        System.arraycopy(data, 0, byteTab, 0, data.size)
        size = data.size * BYTE_SIZE
    }

    constructor(bitSize: Int) {
        byteTab = ByteArray(ceil(bitSize / BYTE_SIZE_F).toInt())
        size = bitSize
    }

    fun addCurrentBitIndex(index: Int) {
        currentBitIndex += index
        if (currentBitIndex < 0) {
            currentBitIndex = 0
        }
    }

    fun getCurrentBitIndex(): Int = currentBitIndex

    fun getData(): ByteArray = byteTab.copyOf()

    fun getMask(index: Int, length: Int): Byte {
        var result = DEFAULT_VALUE.toByte()
        result = (result.toInt() shl index).toByte()
        result = ((result.toInt() and DEFAULT_VALUE) shr index).toByte()
        val shiftRight = BYTE_SIZE - (length + index)
        if (shiftRight > 0) {
            result = (result.toInt() shr shiftRight).toByte()
            result = (result.toInt() shl shiftRight).toByte()
        }
        return result
    }

    fun getNextBoolean(): Boolean {
        return getNextInteger(1) == 1
    }

    fun getNextByte(pSize: Int): ByteArray {
        return getNextByte(pSize, true)
    }

    fun getNextByte(pSize: Int, shift: Boolean): ByteArray {
        val result = ByteArray(ceil(pSize / BYTE_SIZE_F).toInt())

        if (currentBitIndex % BYTE_SIZE != 0) {
            var index = 0
            val max = currentBitIndex + pSize
            while (currentBitIndex < max) {
                val mod = currentBitIndex % BYTE_SIZE
                val modResult = index % BYTE_SIZE
                val length = minOf(
                    max - currentBitIndex,
                    minOf(BYTE_SIZE - mod, BYTE_SIZE - modResult)
                )
                var value = (byteTab[currentBitIndex / BYTE_SIZE].toInt() and getMask(mod, length).toInt()).toByte()

                if (shift || pSize % BYTE_SIZE == 0) {
                    value = if (mod != 0) {
                        (value.toInt() shl minOf(mod, BYTE_SIZE - length)).toByte()
                    } else {
                        (value.toInt() and DEFAULT_VALUE shr modResult).toByte()
                    }
                }

                result[index / BYTE_SIZE] = (result[index / BYTE_SIZE].toInt() or value.toInt()).toByte()
                currentBitIndex += length
                index += length
            }

            if (!shift && pSize % BYTE_SIZE != 0) {
                val lastIndex = result.lastIndex
                result[lastIndex] = (result[lastIndex].toInt() and getMask((max - pSize - 1) % BYTE_SIZE, BYTE_SIZE).toInt()).toByte()
            }
        } else {
            System.arraycopy(byteTab, currentBitIndex / BYTE_SIZE, result, 0, result.size)
            val mod = currentBitIndex % BYTE_SIZE
            var lastBits = pSize % BYTE_SIZE
            if (lastBits == 0) lastBits = BYTE_SIZE
            result[result.lastIndex] = (result.last().toInt() and getMask(mod, lastBits).toInt()).toByte()
            currentBitIndex += pSize
        }

        return result
    }

    fun getNextDate(bitLength: Int, pattern: String): Date? {
        return getNextDate(bitLength, pattern, useBcd = false)
    }

    fun getNextDate(bitLength: Int, pattern: String, useBcd: Boolean): Date? {
        val sdf = SimpleDateFormat(pattern)
        val dateText = if (useBcd) getNextHexaString(bitLength) else getNextString(bitLength)

        return try {
            sdf.parse(dateText)
        } catch (e: ParseException) {
            Log.e(TAG, "Parsing date error. date:$dateText pattern:$pattern", e)
            null
        }
    }

    fun getNextHexaString(bitLength: Int): String {
        return BytesUtils.bytesToStringNoSpace(getNextByte(bitLength, true))
    }

    fun getNextLongSigned(bitLength: Int): Long {
        require(bitLength <= Long.SIZE_BITS) { "Long overflow with length > 64" }
        val value = getNextLong(bitLength)
        val signMask = 1L shl (bitLength - 1)
        return if (value and signMask != 0L) {
            -(signMask - (signMask xor value))
        } else {
            value
        }
    }

    fun getNextIntegerSigned(bitLength: Int): Int {
        require(bitLength <= Int.SIZE_BITS) { "Integer overflow with length > 32" }
        return getNextLongSigned(bitLength).toInt()
    }

    fun getNextLong(bitLength: Int): Long {
        val buffer = ByteBuffer.allocate(Long.SIZE_BYTES)
        var result = 0L
        var readSize = bitLength
        val max = currentBitIndex + bitLength

        while (currentBitIndex < max) {
            val mod = currentBitIndex % BYTE_SIZE
            var current = byteTab[currentBitIndex / BYTE_SIZE].toInt() and getMask(mod, readSize).toInt() and DEFAULT_VALUE
            val dec = max(BYTE_SIZE - (mod + readSize), 0)
            current = (current and DEFAULT_VALUE) ushr dec and DEFAULT_VALUE
            result = (result shl min(readSize, BYTE_SIZE)) or current.toLong()
            val valSize = BYTE_SIZE - mod
            readSize -= valSize
            currentBitIndex = min(currentBitIndex + valSize, max)
        }

        buffer.putLong(result)
        buffer.rewind()
        return buffer.long
    }

    fun getNextInteger(bitLength: Int): Int {
        return getNextLong(bitLength).toInt()
    }

    fun getNextString(bitLength: Int): String {
        return getNextString(bitLength, DEFAULT_CHARSET)
    }

    fun getNextString(bitLength: Int, charset: Charset): String {
        return String(getNextByte(bitLength, true), charset)
    }

    fun getSize(): Int = size

    fun reset() {
        setCurrentBitIndex(0)
    }

    fun clear() {
        Arrays.fill(byteTab, 0)
        reset()
    }

    fun resetNextBits(bitLength: Int) {
        val max = currentBitIndex + bitLength
        while (currentBitIndex < max) {
            val mod = currentBitIndex % BYTE_SIZE
            val length = min(max - currentBitIndex, BYTE_SIZE - mod)
            byteTab[currentBitIndex / BYTE_SIZE] =
                byteTab[currentBitIndex / BYTE_SIZE].toInt()
                    .and(getMask(mod, length).toInt().inv()).toByte()
            currentBitIndex += length
        }
    }

    fun setCurrentBitIndex(index: Int) {
        currentBitIndex = index
    }

    fun setNextBoolean(value: Boolean) {
        setNextInteger(if (value) 1 else 0, 1)
    }

    fun setNextByte(value: ByteArray, bitLength: Int) {
        setNextByte(value, bitLength, true)
    }

    fun setNextByte(value: ByteArray, bitLength: Int, padBefore: Boolean) {
        val totalSize = ceil(bitLength / BYTE_SIZE_F).toInt()
        val buffer = ByteBuffer.allocate(totalSize)
        val padSize = max(totalSize - value.size, 0)

        if (padBefore) repeat(padSize) { buffer.put(0) }
        buffer.put(value, 0, min(totalSize, value.size))
        if (!padBefore) repeat(padSize) { buffer.put(0) }

        val data = buffer.array()

        if (currentBitIndex % BYTE_SIZE != 0) {
            var index = 0
            val maxIndex = currentBitIndex + bitLength

            while (currentBitIndex < maxIndex) {
                val mod = currentBitIndex % BYTE_SIZE
                val modData = index % BYTE_SIZE
                val length = minOf(
                    maxIndex - currentBitIndex,
                    minOf(BYTE_SIZE - mod, BYTE_SIZE - modData)
                )
                var byteVal = data[index / BYTE_SIZE].toInt() and getMask(modData, length).toInt()

                byteVal = if (mod == 0) {
                    byteVal shl min(modData, BYTE_SIZE - length)
                } else {
                    (byteVal and DEFAULT_VALUE) ushr mod
                }

                byteTab[currentBitIndex / BYTE_SIZE] =
                    (byteTab[currentBitIndex / BYTE_SIZE].toInt() or byteVal).toByte()

                currentBitIndex += length
                index += length
            }
        } else {
            System.arraycopy(data, 0, byteTab, currentBitIndex / BYTE_SIZE, data.size)
            currentBitIndex += bitLength
        }
    }

    fun setNextDate(value: Date, pattern: String) {
        setNextDate(value, pattern, useBcd = false)
    }

    fun setNextDate(value: Date, pattern: String, useBcd: Boolean) {
        val formatter = SimpleDateFormat(pattern)
        val dateString = formatter.format(value)
        if (useBcd) {
            setNextHexaString(dateString, dateString.length * 4)
        } else {
            setNextString(dateString, dateString.length * 8)
        }
    }

    fun setNextHexaString(value: String, bitLength: Int) {
        setNextByte(BytesUtils.fromString(value), bitLength)
    }

    fun setNextLong(value: Long, bitLength: Int) {
        require(bitLength <= Long.SIZE_BITS) { "Long overflow with length > 64" }
        setNextValue(value, bitLength, Long.SIZE_BITS - 1)
    }

    fun setNextInteger(value: Int, bitLength: Int) {
        require(bitLength <= Int.SIZE_BITS) { "Integer overflow with length > 32" }
        setNextValue(value.toLong(), bitLength, Int.SIZE_BITS - 1)
    }

    fun setNextString(value: String, bitLength: Int) {
        setNextString(value, bitLength, paddedBefore = true)
    }

    fun setNextString(value: String, bitLength: Int, paddedBefore: Boolean) {
        setNextByte(value.toByteArray(Charset.defaultCharset()), bitLength, paddedBefore)
    }

    protected fun setNextValue(value: Long, bitLength: Int, maxSize: Int) {
        var v = value
        val maxBitValue = 2.0.pow(min(bitLength, maxSize)).toLong()
        if (v > maxBitValue) {
            v = maxBitValue - 1
        }

        var remainingBits = bitLength

        while (remainingBits > 0) {
            val mod = currentBitIndex % BYTE_SIZE
            val byteValue: Byte = if ((mod == 0 && remainingBits <= BYTE_SIZE) || bitLength < BYTE_SIZE - mod) {
                (v shl (BYTE_SIZE - (remainingBits + mod))).toByte()
            } else {
                val shift = remainingBits - java.lang.Long.toBinaryString(v).length
                (v ushr shift - (BYTE_SIZE - java.lang.Long.toBinaryString(v).length - mod)).toByte()
            }

            byteTab[currentBitIndex / BYTE_SIZE] =
                (byteTab[currentBitIndex / BYTE_SIZE].toInt() or byteValue.toInt()).toByte()

            val writtenBits = min(remainingBits, BYTE_SIZE - mod)
            remainingBits -= writtenBits
            currentBitIndex += writtenBits
        }
    }
}
