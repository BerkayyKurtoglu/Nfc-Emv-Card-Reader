package com.berkay.nfcemvcardreader.emvreader.utils

import android.util.Log
import com.berkay.nfcemvcardreader.emvreader.enums.SwEnum
import com.berkay.nfcemvcardreader.emvreader.utils.bitutils.BytesUtils

/**
 * Utility class to handle APDU command responses.
 */
object ResponseUtils {

    private const val TAG = "TerminalTransactionQualifiers"

    /**
     * Checks if the response ends with SW1SW2 == 9000.
     *
     * @param response Byte array response from APDU command.
     * @return True if status is 9000, false otherwise.
     */
    fun isSucceed(response: ByteArray?): Boolean {
        return contains(response, SwEnum.SW_9000)
    }

    /**
     * Checks if the response equals the provided status enum.
     *
     * @param response Byte array response from APDU command.
     * @param status SwEnum to check against.
     * @return True if response equals status.
     */
    fun isEquals(response: ByteArray?, status: SwEnum): Boolean {
        return contains(response, status)
    }

    /**
     * Checks if the response contains any of the given status enums.
     *
     * @param response Byte array response from APDU command.
     * @param statuses Vararg of SwEnum statuses to check.
     * @return True if response status matches any provided statuses.
     */
    fun contains(response: ByteArray?, vararg statuses: SwEnum): Boolean {
        val status = SwEnum.getSW(response)
        if (response != null) {
            val swBytes = BytesUtils.bytesToStringNoSpace(response.copyOfRange(response.size - 2.coerceAtLeast(0), response.size))
            Log.d(TAG, "Response Status <$swBytes> : ${status?.detail ?: "Unknown"}")
        }
        return status != null && status in statuses
    }
}
