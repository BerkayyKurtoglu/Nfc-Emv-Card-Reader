package com.berkay.nfcemvcardreader

import com.berkay.nfcemvcardreader.emvreader.model.EmvCard

sealed interface MainState

data class CardReadState(
    val card: EmvCard = EmvCard()
) : MainState

data object IdleState : MainState

data object LoadingState : MainState

data class ErrorState(
    val throwable: Throwable
) : MainState
