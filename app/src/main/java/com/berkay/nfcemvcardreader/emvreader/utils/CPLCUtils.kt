package com.berkay.nfcemvcardreader.emvreader.utils

import android.util.Log
import com.berkay.nfcemvcardreader.emvreader.iso7816emv.tag.EmvTag
import com.berkay.nfcemvcardreader.emvreader.model.CPLC

/**
 * Utility for parsing Card Production Life-Cycle (CPLC) data according to Global Platform specification.
 */
object CPLCUtils {

    private const val TAG = "CPLCUtils"
    private const val CPLC_SIZE = 42

    /**
     * Parses raw byte array input to extract CPLC data.
     *
     * @param raw Raw input byte array.
     * @return Parsed CPLC object or null if input is invalid.
     */
    fun parse(raw: ByteArray?): CPLC? {
        if (raw == null) return null

        val cplcBytes: ByteArray? = when (raw.size) {
            CPLC_SIZE + 2 -> raw // Raw data without TLV tag
            CPLC_SIZE + 5 -> TlvUtil.getValue(
                raw,
                EmvTag.CARD_PRODUCTION_LIFECYCLE_DATA
            ) // Raw data with TLV tag
            else -> {
                Log.e(TAG, "CPLC data not valid")
                return null
            }
        }

        return CPLC().apply {
            parse(cplcBytes)
            Log.d(TAG, "CPLC decoded: $this")
        }
    }
}
