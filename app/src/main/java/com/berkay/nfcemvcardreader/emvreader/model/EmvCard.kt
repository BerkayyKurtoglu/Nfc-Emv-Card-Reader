package com.berkay.nfcemvcardreader.emvreader.model

import androidx.compose.runtime.Stable
import com.berkay.nfcemvcardreader.emvreader.enums.EmvCardScheme
import com.berkay.nfcemvcardreader.emvreader.model.enums.CardStateEnum
import java.io.Serializable
import java.util.*

/**
 * Represents all relevant data extracted from an EMV card via NFC.
 */
@Stable
data class EmvCard(
    var cplc: CPLC? = null,
    var holderLastname: String? = null,
    var holderFirstname: String? = null,
    var scheme: EmvCardScheme? = null,
    var atrAts: String? = null,
    var atrAtsDescription: List<String>? = null,
    var track2: EmvTrack2? = null,
    var track1: EmvTrack1? = null,
    var bic: String? = null,
    var iban: String? = null,
    var state: CardStateEnum = CardStateEnum.UNKNOWN,
    val applications: MutableList<Application> = mutableListOf()
) : Serializable {

    val cardNumber: String?
        get() = track2?.cardNumber ?: track1?.cardNumber

    val expireDate: Date?
        get() = track2?.expireDate ?: track1?.expireDate

    val resolvedHolderLastname: String?
        get() = holderLastname ?: track1?.holderLastname

    val resolvedHolderFirstname: String?
        get() = holderFirstname ?: track1?.holderFirstname

    override fun equals(other: Any?): Boolean {
        return other is EmvCard &&
                cardNumber != null &&
                cardNumber == other.cardNumber
    }

    override fun hashCode(): Int {
        return cardNumber?.hashCode() ?: 0
    }
}
