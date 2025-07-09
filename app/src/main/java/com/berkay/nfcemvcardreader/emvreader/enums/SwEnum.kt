package com.berkay.nfcemvcardreader.emvreader.enums

import com.berkay.nfcemvcardreader.emvreader.utils.bitutils.BytesUtils


/**
 * Represents a list of APDU response status words and their meanings.
 * Reference: https://www.eftlab.co.uk/index.php/site-map/knowledge-base/118-apdu-response-list
 */
enum class SwEnum(
    val status: ByteArray,
    val detail: String
) {

    SW_61(BytesUtils.fromString("61"), "Command successfully executed; 'XX' bytes of data can be requested with GET RESPONSE"),
    SW_6200(BytesUtils.fromString("6200"), "No information given (NV-RAM not changed)"),
    SW_6201(BytesUtils.fromString("6201"), "NV-RAM not changed"),
    SW_6281(BytesUtils.fromString("6281"), "Returned data may be corrupted"),
    SW_6282(BytesUtils.fromString("6282"), "End of file/record reached before reading Le bytes"),
    SW_6283(BytesUtils.fromString("6283"), "Selected file invalidated"),
    SW_6284(BytesUtils.fromString("6284"), "Selected file not valid; FCI not formatted according to ISO"),
    SW_6285(BytesUtils.fromString("6285"), "Selected file is in termination state"),
    SW_62A2(BytesUtils.fromString("62A2"), "Wrong R-MAC"),
    SW_62A4(BytesUtils.fromString("62A4"), "Card locked during reset"),
    SW_62F1(BytesUtils.fromString("62F1"), "Wrong C-MAC"),
    SW_62F3(BytesUtils.fromString("62F3"), "Internal reset"),
    SW_62F5(BytesUtils.fromString("62F5"), "Default agent locked"),
    SW_62F7(BytesUtils.fromString("62F7"), "Cardholder locked"),
    SW_62F8(BytesUtils.fromString("62F8"), "Basement is current agent"),
    SW_62F9(BytesUtils.fromString("62F9"), "CALC Key Set not unblocked"),
    SW_62(BytesUtils.fromString("62"), "RFU"),
    SW_6300(BytesUtils.fromString("6300"), "No information given (NV-RAM changed)"),
    SW_6381(BytesUtils.fromString("6381"), "File filled up by last write; update not allowed"),
    SW_6382(BytesUtils.fromString("6382"), "Card key not supported"),
    SW_6383(BytesUtils.fromString("6383"), "Reader key not supported"),
    SW_6384(BytesUtils.fromString("6384"), "Plaintext transmission not supported"),
    SW_6385(BytesUtils.fromString("6385"), "Secured transmission not supported"),
    SW_6386(BytesUtils.fromString("6386"), "Volatile memory not available"),
    SW_6387(BytesUtils.fromString("6387"), "Non-volatile memory not available"),
    SW_6388(BytesUtils.fromString("6388"), "Invalid key number"),
    SW_6389(BytesUtils.fromString("6389"), "Invalid key length"),
    SW_63C0(BytesUtils.fromString("63C0"), "Verify failed; no tries left"),
    SW_63C1(BytesUtils.fromString("63C1"), "Verify failed; 1 try left"),
    SW_63C2(BytesUtils.fromString("63C2"), "Verify failed; 2 tries left"),
    SW_63C3(BytesUtils.fromString("63C3"), "Verify failed; 3 tries left"),
    SW_63(BytesUtils.fromString("63"), "RFU"),
    SW_6400(BytesUtils.fromString("6400"), "No information given (NV-RAM not changed)"),
    SW_6401(BytesUtils.fromString("6401"), "Command timeout"),
    SW_64(BytesUtils.fromString("64"), "RFU"),
    SW_6500(BytesUtils.fromString("6500"), "No information given"),
    SW_6501(BytesUtils.fromString("6501"), "Write error or hardware failure"),
    SW_6581(BytesUtils.fromString("6581"), "Memory failure"),
    SW_65(BytesUtils.fromString("65"), "RFU"),
    SW_6669(BytesUtils.fromString("6669"), "Incorrect encryption/decryption padding"),
    SW_66(BytesUtils.fromString("66"), "-"),
    SW_6700(BytesUtils.fromString("6700"), "Wrong length"),
    SW_67(BytesUtils.fromString("67"), "Incorrect length (ISO 7816-3)"),
    SW_6800(BytesUtils.fromString("6800"), "Request function not supported by card"),
    SW_6881(BytesUtils.fromString("6881"), "Logical channel not supported"),
    SW_6882(BytesUtils.fromString("6882"), "Secure messaging not supported"),
    SW_6883(BytesUtils.fromString("6883"), "Last command of the chain expected"),
    SW_6884(BytesUtils.fromString("6884"), "Command chaining not supported"),
    SW_68(BytesUtils.fromString("68"), "RFU"),
    SW_6900(BytesUtils.fromString("6900"), "Command not allowed"),
    SW_6981(BytesUtils.fromString("6981"), "Command incompatible with file structure"),
    SW_6982(BytesUtils.fromString("6982"), "Security condition not satisfied"),
    SW_6983(BytesUtils.fromString("6983"), "Authentication method blocked"),
    SW_6984(BytesUtils.fromString("6984"), "Referenced data blocked"),
    SW_6985(BytesUtils.fromString("6985"), "Conditions of use not satisfied"),
    SW_6986(BytesUtils.fromString("6986"), "No current EF"),
    SW_6987(BytesUtils.fromString("6987"), "Missing secure messaging object"),
    SW_6988(BytesUtils.fromString("6988"), "Incorrect secure messaging object"),
    SW_6996(BytesUtils.fromString("6996"), "Data must be updated again"),
    SW_69F0(BytesUtils.fromString("69F0"), "Permission denied"),
    SW_69F1(BytesUtils.fromString("69F1"), "Permission denied - missing privilege"),
    SW_69(BytesUtils.fromString("69"), "RFU"),
    SW_6A00(BytesUtils.fromString("6A00"), "Incorrect P1/P2"),
    SW_6A80(BytesUtils.fromString("6A80"), "Invalid parameters in data field"),
    SW_6A81(BytesUtils.fromString("6A81"), "Function not supported"),
    SW_6A82(BytesUtils.fromString("6A82"), "File not found"),
    SW_6A83(BytesUtils.fromString("6A83"), "Record not found"),
    SW_6A84(BytesUtils.fromString("6A84"), "Insufficient memory space"),
    SW_6A85(BytesUtils.fromString("6A85"), "Lc inconsistent with TLV structure"),
    SW_6A86(BytesUtils.fromString("6A86"), "Incorrect P1 or P2"),
    SW_6A87(BytesUtils.fromString("6A87"), "Lc inconsistent with P1-P2"),
    SW_6A88(BytesUtils.fromString("6A88"), "Referenced data not found"),
    SW_6A89(BytesUtils.fromString("6A89"), "File already exists"),
    SW_6A8A(BytesUtils.fromString("6A8A"), "DF name already exists"),
    SW_6AF0(BytesUtils.fromString("6AF0"), "Wrong parameter value"),
    SW_6A(BytesUtils.fromString("6A"), "RFU"),
    SW_6B00(BytesUtils.fromString("6B00"), "Incorrect parameters P1-P2"),
    SW_6B(BytesUtils.fromString("6B"), "Reference incorrect (ISO 7816-3)"),
    SW_6C00(BytesUtils.fromString("6C00"), "Incorrect P3 length"),
    SW_6C(BytesUtils.fromString("6C"), "Exact Le expected"),
    SW_6D00(BytesUtils.fromString("6D00"), "Instruction not supported or invalid"),
    SW_6D(BytesUtils.fromString("6D"), "Instruction not programmed or invalid"),
    SW_6E00(BytesUtils.fromString("6E00"), "Class not supported"),
    SW_6E(BytesUtils.fromString("6E"), "Instruction class not supported"),
    SW_6F00(BytesUtils.fromString("6F00"), "Command aborted; diagnosis not possible"),
    SW_6FFF(BytesUtils.fromString("6FFF"), "Card dead"),
    SW_6F(BytesUtils.fromString("6F"), "No precise diagnosis"),
    SW_9000(BytesUtils.fromString("9000"), "Command successfully executed");

    companion object {

        /**
         * Returns the matching SwEnum based on the last two bytes of the response.
         */
        fun getSW(data: ByteArray?): SwEnum? {
            if (data == null || data.size < 2) return null
            return entries.firstOrNull { sw ->
                when (sw.status.size) {
                    1 -> data[data.size - 2] == sw.status[0]
                    2 -> data[data.size - 2] == sw.status[0] && data[data.size - 1] == sw.status[1]
                    else -> false
                }
            }
        }
    }
}
