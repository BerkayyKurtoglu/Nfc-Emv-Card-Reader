package com.berkay.nfcemvcardreader.emvreader.model.enums

import com.berkay.nfcemvcardreader.emvreader.model.enums.IKeyEnum

/**
 * Represents position 3 of the service code, indicating usage restrictions and PIN requirements.
 */
enum class ServiceCode3Enum(
    private val value: Int,
    val allowedServices: String,
    val pinRequirements: String
) : IKeyEnum {

    NO_RESTRICTION_PIN_REQUIRED(0, "No restrictions", "PIN required"),
    NO_RESTRICTION(1, "No restrictions", "None"),
    GOODS_SERVICES(2, "Goods and services only", "None"),
    ATM_ONLY(3, "ATM only", "PIN required"),
    CASH_ONLY(4, "Cash only", "None"),
    GOODS_SERVICES_PIN_REQUIRED(5, "Goods and services only", "PIN required"),
    NO_RESTRICTION_PIN_IF_PED(6, "No restrictions", "Prompt for PIN if PED present"),
    GOODS_SERVICES_PIN_IF_PED(7, "Goods and services only", "Prompt for PIN if PED present");

    override fun getKey(): Int = value
}
