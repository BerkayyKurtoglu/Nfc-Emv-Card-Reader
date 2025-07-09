package com.berkay.nfcemvcardreader

import androidx.lifecycle.ViewModel
import com.berkay.nfcemvcardreader.emvreader.model.EmvCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel() : ViewModel() {

    private val _state = MutableStateFlow<MainState>(IdleState)
    val state = _state.asStateFlow()

    fun handleAction(
        action: MainAction
    ) {
        when (action) {
            is MainAction.OnCardRead -> actionOnCardRead(action.emvCard)
            is MainAction.OnError -> actionOnError(action.throwable)
            MainAction.OnIdle -> actionOnIdle()
            MainAction.OnInProgress -> actionOnInProgress()
        }
    }

    private fun actionOnCardRead(card: EmvCard) {
        _state.update {
            CardReadState(
                card = card
            )
        }
    }

    private fun actionOnInProgress() {
        _state.update {
            LoadingState
        }
    }

    private fun actionOnIdle() {
        _state.update {
            IdleState
        }
    }

    private fun actionOnError(throwable: Throwable) {
        _state.update {
            ErrorState(
                throwable = throwable
            )
        }
    }

}
