package com.berkay.nfcemvcardreader.emvreader.model

/**
 * Describes an Application File Locator (AFL) entry, which specifies where to find EMV records.
 */
data class Afl(
    var sfi: Int = 0,
    var firstRecord: Int = 0,
    var lastRecord: Int = 0,
    var offlineAuthentication: Boolean = false
)
