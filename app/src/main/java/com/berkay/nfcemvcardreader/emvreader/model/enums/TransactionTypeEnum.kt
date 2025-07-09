package com.berkay.nfcemvcardreader.emvreader.model.enums

import com.berkay.nfcemvcardreader.emvreader.model.enums.IKeyEnum

/**
 * Represents the EMV transaction type based on ISO 8583 values.
 */
enum class TransactionTypeEnum(
    val value: Int
) : IKeyEnum {

    PURCHASE(0x00),             // '00' → Purchase transaction
    CASH_ADVANCE(0x01),         // '01' → Cash advance
    CASHBACK(0x09),             // '09' → Purchase with cashback
    REFUND(0x20),               // '20' → Refund transaction
    LOADED(0xFE),               // Loaded transaction (Geldkarte)
    UNLOADED(0xFF);             // Unloaded transaction (Geldkarte)

    override fun getKey(): Int = value
}
