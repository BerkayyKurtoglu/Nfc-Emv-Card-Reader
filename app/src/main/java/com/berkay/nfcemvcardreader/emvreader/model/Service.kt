package com.berkay.nfcemvcardreader.emvreader.model

import com.berkay.nfcemvcardreader.emvreader.model.enums.ServiceCode1Enum
import com.berkay.nfcemvcardreader.emvreader.model.enums.ServiceCode2Enum
import com.berkay.nfcemvcardreader.emvreader.model.enums.ServiceCode3Enum
import com.berkay.nfcemvcardreader.emvreader.utils.EnumUtils
import com.berkay.nfcemvcardreader.emvreader.utils.bitutils.BitUtils
import com.berkay.nfcemvcardreader.emvreader.utils.bitutils.BytesUtils
import java.io.Serializable

/**
 * Represents a decoded service code from Track 1 or 2 data.
 */

data class Service(
    val serviceCode1: ServiceCode1Enum?,
    val serviceCode2: ServiceCode2Enum?,
    val serviceCode3: ServiceCode3Enum?
) : Serializable {
    companion object {
        fun fromRaw(data: String?): Service? {
            if (data.isNullOrBlank() || data.length != 3) return null
            val padded = data.padEnd(4, '0')
            val bit = BitUtils(BytesUtils.fromString(padded))
            return Service(
                EnumUtils.getValue(bit.getNextInteger(4), ServiceCode1Enum::class.java) as? ServiceCode1Enum,
                EnumUtils.getValue(bit.getNextInteger(4), ServiceCode2Enum::class.java) as? ServiceCode2Enum,
                EnumUtils.getValue(bit.getNextInteger(4), ServiceCode3Enum::class.java) as? ServiceCode3Enum
            )
        }
    }
}
