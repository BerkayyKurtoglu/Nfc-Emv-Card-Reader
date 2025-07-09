package com.berkay.nfcemvcardreader.emvreader.model

import com.berkay.nfcemvcardreader.emvreader.model.enums.CountryCodeEnum
import com.berkay.nfcemvcardreader.emvreader.model.enums.CurrencyEnum
import com.berkay.nfcemvcardreader.emvreader.model.enums.TransactionTypeEnum
import com.berkay.nfcemvcardreader.emvreader.parser.apdu.annotation.Data
import com.berkay.nfcemvcardreader.emvreader.parser.apdu.impl.AbstractByteBean
import com.berkay.nfcemvcardreader.emvreader.parser.apdu.impl.DataFactory
import java.io.Serializable
import java.util.Date

/**
 * Represents a single EMV transaction log entry read from the card.
 * Used when reading transaction history via READ RECORD.
 */
data class EmvTransactionRecord(
    /**
     * Amount authorized for the transaction.
     * Format is BCD and must be interpreted according to currency.
     */
    @Data(index = 1, size = 48, format = DataFactory.BCD_FORMAT, tag = "9f02")
    val amount: Float? = null,

    /**
     * Cryptogram information data (hex string).
     */
    @Data(index = 2, size = 8, readHexa = true, tag = "9f27")
    val cyptogramData: String? = null,

    /**
     * Country code of the terminal where the transaction occurred.
     */
    @Data(index = 3, size = 16, tag = "9f1a")
    val terminalCountry: CountryCodeEnum? = null,

    /**
     * Currency used in the transaction.
     */
    @Data(index = 4, size = 16, tag = "5f2a")
    val currency: CurrencyEnum? = null,

    /**
     * Date of the transaction.
     */
    @Data(index = 5, size = 24, dateStandard = DataFactory.BCD_DATE, format = "yyMMdd", tag = "9a")
    val date: Date? = null,

    /**
     * Type of transaction (e.g., Payment, Withdrawal).
     */
    @Data(index = 6, size = 8, readHexa = true, tag = "9c")
    val transactionType: TransactionTypeEnum? = null,

    /**
     * Time of the transaction.
     */
    @Data(index = 7, size = 24, dateStandard = DataFactory.BCD_DATE, format = "HHmmss", tag = "9f21")
    val time: Date? = null
) : AbstractByteBean<EmvTransactionRecord>(), Serializable

///**
// * Represents a single EMV transaction log entry read from the card.
// * Used when reading transaction history via READ RECORD.
// */
//class EmvTransactionRecord : AbstractByteBean<EmvTransactionRecord>(), Serializable {
//
//    /**
//     * Amount authorized for the transaction.
//     * Format is BCD and must be interpreted according to currency.
//     */
//    @Data(index = 1, size = 48, format = DataFactory.BCD_FORMAT, tag = "9f02")
//    var amount: Float? = null
//
//    /**
//     * Cryptogram information data (hex string).
//     */
//    @Data(index = 2, size = 8, readHexa = true, tag = "9f27")
//    var cyptogramData: String? = null
//
//    /**
//     * Country code of the terminal where the transaction occurred.
//     */
//    @Data(index = 3, size = 16, tag = "9f1a")
//    var terminalCountry: CountryCodeEnum? = null
//
//    /**
//     * Currency used in the transaction.
//     */
//    @Data(index = 4, size = 16, tag = "5f2a")
//    var currency: CurrencyEnum? = null
//
//    /**
//     * Date of the transaction.
//     */
//    @Data(index = 5, size = 24, dateStandard = DataFactory.BCD_DATE, format = "yyMMdd", tag = "9a")
//    var date: Date? = null
//
//    /**
//     * Type of transaction (e.g., Payment, Withdrawal).
//     */
//    @Data(index = 6, size = 8, readHexa = true, tag = "9c")
//    var transactionType: TransactionTypeEnum? = null
//
//    /**
//     * Time of the transaction.
//     */
//    @Data(index = 7, size = 24, dateStandard = DataFactory.BCD_DATE, format = "HHmmss", tag = "9f21")
//    var time: Date? = null
//}

