package com.berkay.nfcemvcardreader.emvreader.utils

import android.util.Log
import com.berkay.nfcemvcardreader.emvreader.model.EmvTrack1
import com.berkay.nfcemvcardreader.emvreader.model.EmvTrack2
import com.berkay.nfcemvcardreader.emvreader.model.Service
import com.berkay.nfcemvcardreader.emvreader.utils.bitutils.BytesUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.regex.Pattern

/**
 * Utility class to extract EMV track data from raw bytes.
 */
object TrackUtils {

    private const val TAG = "TrackUtils"

    const val CARD_HOLDER_NAME_SEPARATOR = "/"

    private val TRACK2_EQUIVALENT_PATTERN = Pattern.compile("([0-9]{1,19})D([0-9]{4})([0-9]{3})?(.*)")

    private val TRACK1_PATTERN = Pattern.compile(
        "%?([A-Z])([0-9]{1,19})(\\?[0-9])?\\^([^\\^]{2,26})\\^([0-9]{4}|\\^)([0-9]{3}|\\^)([^?]+)\\??"
    )

    /**
     * Extracts Track 2 Equivalent data from raw byte array.
     * Returns an EmvTrack2 object or null if extraction fails.
     */
    fun extractTrack2EquivalentData(rawTrack2: ByteArray?): EmvTrack2? {
        if (rawTrack2 == null) return null

        val data = BytesUtils.bytesToStringNoSpace(rawTrack2)
        val matcher = TRACK2_EQUIVALENT_PATTERN.matcher(data)
        if (!matcher.find()) return null

        val cardNumber = matcher.group(1)
        val expireDate = try {
            matcher.group(2)?.let {
                val parsed = SimpleDateFormat("yyMM", Locale.getDefault()).parse(it)
                parsed?.let { lastDayOfMonth(it) }
            }
        } catch (e: ParseException) {
            Log.e(TAG, "Unparsable expire card date: ${e.message}", e)
            return null
        }

        val service = Service.fromRaw(matcher.group(3))

        return EmvTrack2(
            raw = rawTrack2,
            cardNumber = cardNumber,
            expireDate = expireDate,
            service = service
        )
    }

    /**
     * Extracts Track 1 data from raw byte array.
     * Returns an EmvTrack1 object or null if extraction fails.
     */
    fun extractTrack1Data(rawTrack1: ByteArray?): EmvTrack1? {
        if (rawTrack1 == null) return null
        val matcher = TRACK1_PATTERN.matcher(String(rawTrack1))
        if (!matcher.find()) return null

        val formatCode = matcher.group(1)
        val cardNumber = matcher.group(2)

        val group4 = matcher.group(4)
        val name = group4?.trim()?.split(CARD_HOLDER_NAME_SEPARATOR)
        val holderLastname = name?.getOrNull(0)?.trim()?.ifBlank { null }
        val holderFirstname = name?.getOrNull(1)?.trim()?.ifBlank { null }

        println("rawTrack1: $rawTrack1")
        val expireDate = try {
            matcher.group(5)?.let {
                val parsedDate = SimpleDateFormat("yyMM", Locale.getDefault()).parse(it)
                parsedDate?.let { lastDayOfMonth(it) }
            }
        } catch (e: ParseException) {
            Log.e(TAG, "Unparsable expire card date: ${e.message}", e)
            return null
        }

        val service = Service.fromRaw(matcher.group(6))

        return EmvTrack1(
            raw = rawTrack1,
            formatCode = formatCode,
            cardNumber = cardNumber,
            expireDate = expireDate,
            holderLastname = holderLastname,
            holderFirstname = holderFirstname,
            service = service
        )
    }

    /**
     * Returns the last day of the month for the given date.
     */
    private fun lastDayOfMonth(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }
}
