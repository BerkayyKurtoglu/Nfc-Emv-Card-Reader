package com.berkay.nfcemvcardreader.emvreader.iso7816emv

/**
 * Wrapper class for byte arrays to provide proper equals and hashCode implementations.
 * This is essential when using byte arrays as keys in collections like maps.
 */
class ByteArrayWrapper private constructor(private val data: ByteArray) {

    companion object {
        fun wrapperAround(data: ByteArray?): ByteArrayWrapper {
            requireNotNull(data) { "Data must not be null" }
            return ByteArrayWrapper(data)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ByteArrayWrapper) return false
        return data.contentEquals(other.data)
    }

    override fun hashCode(): Int = data.contentHashCode()
}
