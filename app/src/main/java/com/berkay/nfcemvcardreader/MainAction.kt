package com.berkay.nfcemvcardreader

import com.berkay.nfcemvcardreader.emvreader.model.EmvCard

sealed interface MainAction {

    data class OnCardRead(val emvCard: EmvCard) : MainAction
    data class OnError(val throwable: Throwable) : MainAction
    data object OnInProgress : MainAction
    data object OnIdle : MainAction

}
