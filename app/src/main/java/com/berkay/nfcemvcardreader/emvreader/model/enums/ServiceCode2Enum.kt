package com.berkay.nfcemvcardreader.emvreader.model.enums

import com.berkay.nfcemvcardreader.emvreader.model.enums.IKeyEnum

/**
 * Represents position 2 of the service code, indicating authorization processing rules.
 */
enum class ServiceCode2Enum(
    private val value: Int,
    val authorizationProcessing: String
) : IKeyEnum {

    NORMAL(0, "Normal"),
    BY_ISSUER(2, "By issuer"),
    BY_ISSUER_WIHOUT_BI_AGREEMENT(4, "By issuer unless explicit bilateral agreement applies");

    override fun getKey(): Int = value
}
