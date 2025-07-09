package com.berkay.nfcemvcardreader.emvreader.utils

import org.apache.commons.collections4.MultiValuedMap
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

/**
 * Utility to find ATR or ATS descriptions to identify card manufacturer or type.
 * ATR (Answer to Reset) is the identification response for contact cards.
 * ATS (Answer to Select) is the response for contactless (NFC) cards.
 */
object AtrUtils {

    private const val TAG = "AtrUtils"

    private val valuedMap: MultiValuedMap<String, String> = ArrayListValuedHashMap()

    init {
        try {
            AtrUtils::class.java.getResourceAsStream("/smartcard_list.txt")?.use { isStream ->
                InputStreamReader(isStream, StandardCharsets.UTF_8).use { isr ->
                    BufferedReader(isr).useLines { lines ->
                        var lineNumber = 0
                        var currentATR: String? = null
                        lines.forEach { line ->
                            lineNumber++
                            when {
                                line.startsWith("#") || line.trim().isEmpty() || line.contains("http") -> {
                                    // Skip comments, empty lines, and URLs
                                }
                                line.startsWith("\t") && currentATR != null -> {
                                    valuedMap.put(currentATR, line.trim())
                                }
                                line.startsWith("3") -> {
                                    currentATR = line.uppercase()
                                        .filterNot { it.isWhitespace() }
                                        .replace(Regex("9000$"), "")
                                }
                                else -> {
                                Log.e(TAG, "Unexpected line at $lineNumber: currentATR=$currentATR line='$line'")
                                }
                            }
                        }
                    }
                }
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    /**
     * Finds card description(s) based on ATR string.
     * Returns collection of descriptions or null if not found.
     */
    fun getDescription(atr: String?): List<String>? {
        if (atr.isNullOrBlank()) return null
        val valStr = atr.filterNot { it.isWhitespace() }.uppercase()
        valuedMap.keySet().forEach { key ->
            if (valStr.matches(Regex("^$key$"))) {
                return valuedMap.get(key).toList()
            }
        }
        return null
    }

    /**
     * Finds card description(s) based on ATS string.
     * Returns collection of descriptions, or empty list if none found.
     */
    fun getDescriptionFromAts(ats: String?): List<String> {
        val ret = ArrayList<String>()
        if (ats.isNullOrBlank()) return ret
        val valStr = ats.filterNot { it.isWhitespace() }.replace(Regex("9000$"), "")
        valuedMap.keySet().forEach { key ->
            var j = valStr.length - 1
            var i = key.length - 1
            while (i >= 0) {
                if (key[i] == '.' || key[i] == valStr[j]) {
                    j--
                    i--
                    if (j < 0) {
                        val subKey =
                            key.substring(key.length - valStr.length, key.length).replace(".", "")
                        if (subKey.isNotEmpty()) {
                            ret.addAll(valuedMap.get(key))
                        }
                        break
                    }
                } else if (j != valStr.length - 1) {
                    j = valStr.length - 1
                } else if (i == key.length - 1) {
                    break
                } else {
                    i--
                }
            }
        }
        return ret
    }
}
