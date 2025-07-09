package com.berkay.nfcemvcardreader.emvreader.parser.apdu

import com.berkay.nfcemvcardreader.emvreader.iso7816emv.TagAndLength

/**
 * Interface for parsing byte data based on a tag and length list.
 */
interface IFile {
    fun parse(data: ByteArray, list: Collection<TagAndLength>)
}
