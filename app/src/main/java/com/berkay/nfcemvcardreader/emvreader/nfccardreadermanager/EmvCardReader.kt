package com.berkay.nfcemvcardreader.emvreader.nfccardreadermanager

import android.Manifest
import android.app.Activity
import android.content.Context
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.IsoDep
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.berkay.nfcemvcardreader.emvreader.PcscProvider
import com.berkay.nfcemvcardreader.emvreader.parser.EmvTemplate

private const val VIBRATION_MS = 100L
private const val VIBRATION_AMPLITUDE = 10
private const val NFC_PRESENCE_CHECK_DELAY = 250

class EmvCardReader(
    private val activity: Activity,
    lifecycle: Lifecycle,
) {
    private val _managerState = MutableStateFlow<EmvCardReaderState>(EmvCardReaderState.Idle)
    val managerState: StateFlow<EmvCardReaderState> = _managerState.asStateFlow()

    private val nfcAdapter: NfcAdapter? = NfcAdapter.getDefaultAdapter(activity)
    private var lastFailedTagId: ByteArray? = null

    init {
        lifecycle.addObserver(object : DefaultLifecycleObserver {
            @RequiresApi(Build.VERSION_CODES.O)
            @RequiresPermission(Manifest.permission.VIBRATE)
            override fun onResume(owner: LifecycleOwner) {
                nfcAdapter?.enableReaderMode(
                    activity,
                    { tag ->
                        handleTag(tag)
                    },
                    FLAGS,
                    OPTIONS
                )
            }

            override fun onPause(owner: LifecycleOwner) {
                nfcAdapter?.disableReaderMode(activity)
            }
        })
    }

    @RequiresPermission(Manifest.permission.VIBRATE)
    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleTag(tag: Tag) {
        if (lastFailedTagId?.contentEquals(tag.id) == true) return

        try {
            _managerState.value = EmvCardReaderState.InProgress
            val isoDep = IsoDep.get(tag) ?: return

            val vibrationService = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val manager = activity.getSystemService(VibratorManager::class.java)
                manager?.defaultVibrator
            } else {
                activity.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
            }
            vibrationService?.vibrate(
                VibrationEffect.createOneShot(
                    VIBRATION_MS,
                    VIBRATION_AMPLITUDE
                )
            )

            isoDep.connect()

            val provider = PcscProvider().apply { setTagCommunicator(isoDep) }

            val config = EmvTemplate.Config()
                .setContactLess(true)
                .setReadAllAids(true)
                .setReadTransactions(true)
                .setRemoveDefaultParsers(false)
                .setReadAt(true)

            val parser = EmvTemplate.Builder()
                .setProvider(provider)
                .setConfig(config)
                .build()

            val card = parser.readEmvCard()
            _managerState.value = EmvCardReaderState.CardRead(card)
            lastFailedTagId = null
            isoDep.close()
        } catch (e: Exception) {
            lastFailedTagId = tag.id
            _managerState.value = EmvCardReaderState.Error(e)
            Handler(Looper.getMainLooper()).postDelayed({ lastFailedTagId = null }, 1000)
        }
    }

    companion object {
        private const val FLAGS = NfcAdapter.FLAG_READER_NFC_A or
                NfcAdapter.FLAG_READER_NFC_B or
                NfcAdapter.FLAG_READER_NFC_F or
                NfcAdapter.FLAG_READER_NFC_V or
                NfcAdapter.FLAG_READER_NFC_BARCODE or
                NfcAdapter.FLAG_READER_NO_PLATFORM_SOUNDS

        private val OPTIONS = Bundle().apply {
            putInt(NfcAdapter.EXTRA_READER_PRESENCE_CHECK_DELAY, NFC_PRESENCE_CHECK_DELAY)
        }
    }
}
