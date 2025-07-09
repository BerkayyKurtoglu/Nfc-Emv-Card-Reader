package com.berkay.nfcemvcardreader.emvreader.model.enums

/**
 * Interface for enums that expose an integer key.
 */
interface IKeyEnum {
    /**
     * Returns the key value associated with the enum.
     */
    fun getKey(): Int
}
