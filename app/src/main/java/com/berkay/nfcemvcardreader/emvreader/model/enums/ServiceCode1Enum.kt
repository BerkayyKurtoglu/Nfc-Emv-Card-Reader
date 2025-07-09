package com.berkay.nfcemvcardreader.emvreader.model.enums

import com.berkay.nfcemvcardreader.emvreader.model.enums.IKeyEnum

/**
 * Represents position 1 of the service code, indicating interchange rules and technology.
 */
enum class ServiceCode1Enum(
    private val value: Int,
    val interchange: String,
    val technology: String
) : IKeyEnum {

    INTERNATIONNAL(1, "International interchange", "None"),
    INTERNATIONNAL_ICC(2, "International interchange", "Integrated circuit card"),
    NATIONAL(5, "National interchange", "None"),
    NATIONAL_ICC(6, "National interchange", "Integrated circuit card"),
    PRIVATE(7, "Private", "None");

    override fun getKey(): Int = value
}
