package com.berkay.nfcemvcardreader.emvreader.utils

import android.util.Log
import com.berkay.nfcemvcardreader.emvreader.model.enums.IKeyEnum

/**
 * Utility methods for working with enums that implement IKeyEnum.
 */
object EnumUtils {

    private const val TAG = "EnumUtils"

    /**
     * Returns the enum constant that matches the given key, or null if not found.
     */
    fun getValue(key: Int, enumClass: Class<out IKeyEnum>): IKeyEnum? {
        return try {
            val enumConstants = enumClass.enumConstants
            if (enumConstants != null && enumConstants.isNotEmpty() && enumConstants[0] is Enum<*>) {
                for (value in enumConstants) {
                    if (value.getKey() == key) {
                        return value
                    }
                }
            }
            Log.e(TAG, "Unknown value: $key for Enum: ${enumClass.name}")
            null
        } catch (e: Exception) {
            Log.e(TAG, "Enum casting error: ${e.message}", e)
            null
        }
    }
}
