package com.berkay.nfcemvcardreader

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.berkay.nfcemvcardreader.emvreader.model.EmvCard
import com.berkay.nfcemvcardreader.emvreader.nfccardreadermanager.EmvCardReader
import com.berkay.nfcemvcardreader.emvreader.nfccardreadermanager.EmvCardReaderState
import com.berkay.nfcemvcardreader.ui.theme.NfcEmvCardReaderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NfcEmvCardReaderTheme {
                val lifecycleOwner = LocalLifecycleOwner.current.lifecycle
                val activity = LocalContext.current as? Activity
                val cardReader = remember(activity, lifecycleOwner) {
                    activity?.let { act ->
                        EmvCardReader(
                            activity = act,
                            lifecycle = lifecycleOwner
                        )
                    }
                }
                val viewModel: MainViewModel by viewModels()
                val state by viewModel.state.collectAsStateWithLifecycle()

                ListenForManager(cardReader, viewModel)

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                ) { innerPadding ->
                    MainScreen(state, innerPadding)
                }
            }
        }
    }
}

@Composable
private fun ListenForManager(cardReader: EmvCardReader?, viewModel: MainViewModel) {
    LaunchedEffect(cardReader) {
        cardReader?.managerState?.collect { state ->
            when (state) {
                is EmvCardReaderState.CardRead -> {
                    viewModel.handleAction(MainAction.OnCardRead(state.smartCard))
                }

                is EmvCardReaderState.Error -> {
                    viewModel.handleAction(MainAction.OnError(state.throwable))
                }

                EmvCardReaderState.InProgress -> {
                    viewModel.handleAction(MainAction.OnInProgress)
                }

                EmvCardReaderState.Idle -> {
                    viewModel.handleAction(MainAction.OnIdle)
                }
            }
        }
    }
}

@Composable
fun MainScreen(state: MainState, innerPadding: PaddingValues) {
    when (state) {
        is CardReadState -> CardReadScreen(state.card, innerPadding)
        is ErrorState -> ErrorScreen(state.throwable, innerPadding)
        IdleState -> IdleScreen(innerPadding)
        LoadingState -> LoadingScreen()
    }
}

@Composable
private fun CardReadScreen(emvCard: EmvCard, innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(innerPadding)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(emvCard.toString())
    }
}

@Composable
private fun ErrorScreen(throwable: Throwable, innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(throwable.localizedMessage ?: "Unknown error")
    }
}

@Composable
private fun LoadingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun IdleScreen(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome to emv card reader! " +
                    "Please make sure nfc settings opened",
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NfcEmvCardReaderTheme {
        Greeting("Android")
    }
}