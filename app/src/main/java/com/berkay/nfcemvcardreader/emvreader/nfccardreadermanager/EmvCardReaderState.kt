package com.berkay.nfcemvcardreader.emvreader.nfccardreadermanager

import com.berkay.nfcemvcardreader.emvreader.model.EmvCard

sealed interface EmvCardReaderState {
    data object Idle : EmvCardReaderState
    data object InProgress : EmvCardReaderState
    data class CardRead(val smartCard: EmvCard) : EmvCardReaderState
    data class Error(val throwable: Throwable) : EmvCardReaderState
}
