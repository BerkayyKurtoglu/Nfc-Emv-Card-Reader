package com.berkay.nfcemvcardreader.emvreader.model.enums

/**
 * Represents the current state of the EMV card.
 */
enum class CardStateEnum : IKeyEnum {
    UNKNOWN,
    LOCKED,
    ACTIVE;

    override fun getKey(): Int = 0
}
