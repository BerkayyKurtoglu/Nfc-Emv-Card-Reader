package com.berkay.nfcemvcardreader.emvreader.model

import java.util.Date

/**
 * Represents data parsed from magnetic stripe Track 1.
 */
data class EmvTrack1(
    val raw: ByteArray? = null,
    val formatCode: String? = null,
    val cardNumber: String? = null,
    val expireDate: Date? = null,
    val holderLastname: String? = null,
    val holderFirstname: String? = null,
    val service: Service? = null
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EmvTrack1

        if (raw != null) {
            if (other.raw == null) return false
            if (!raw.contentEquals(other.raw)) return false
        } else if (other.raw != null) return false
        if (formatCode != other.formatCode) return false
        if (cardNumber != other.cardNumber) return false
        if (expireDate != other.expireDate) return false
        if (holderLastname != other.holderLastname) return false
        if (holderFirstname != other.holderFirstname) return false
        if (service != other.service) return false

        return true
    }

    override fun hashCode(): Int {
        var result = raw?.contentHashCode() ?: 0
        result = 31 * result + (formatCode?.hashCode() ?: 0)
        result = 31 * result + (cardNumber?.hashCode() ?: 0)
        result = 31 * result + (expireDate?.hashCode() ?: 0)
        result = 31 * result + (holderLastname?.hashCode() ?: 0)
        result = 31 * result + (holderFirstname?.hashCode() ?: 0)
        result = 31 * result + (service?.hashCode() ?: 0)
        return result
    }
}
