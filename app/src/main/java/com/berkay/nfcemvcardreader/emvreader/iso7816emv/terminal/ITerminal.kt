package papara.framework.nfckit.iso7816emv.terminal

import com.berkay.nfcemvcardreader.emvreader.iso7816emv.TagAndLength

interface ITerminal {

    /**
     * Constructs a value from the given tag and length.
     *
     * @param tagAndLength The tag and length object.
     * @return Byte array representing the constructed tag value.
     */
    fun constructValue(tagAndLength: TagAndLength): ByteArray
}
