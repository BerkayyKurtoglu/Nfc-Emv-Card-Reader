package com.berkay.nfcemvcardreader.emvreader.parser.apdu.annotation

import com.berkay.nfcemvcardreader.emvreader.utils.bitutils.BitUtils


/**
 * Annotation used to describe metadata about a field in an APDU structure.
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class Data(
    val format: String = BitUtils.DATE_FORMAT,
    val dateStandard: Int = 0,
    val index: Int,
    val readHexa: Boolean = false,
    val size: Int,
    val tag: String = ""
)
