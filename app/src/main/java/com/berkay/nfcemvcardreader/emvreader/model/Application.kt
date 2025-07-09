package com.berkay.nfcemvcardreader.emvreader.model

import com.berkay.nfcemvcardreader.emvreader.model.enums.ApplicationStepEnum
import java.io.Serializable

/**
 * Represents an EMV application including metadata, AID, and transaction info.
 */
private const val UNKNOWN: Int = -1

data class Application(
    var aid: ByteArray? = null,
    var readingStep: ApplicationStepEnum = ApplicationStepEnum.NOT_SELECTED,
    var applicationLabel: String? = null,
    var transactionCounter: Int = UNKNOWN,
    var leftPinTry: Int = UNKNOWN,
    var priority: Int = 1,
    var amount: Float = UNKNOWN.toFloat(),
    var listTransactions: List<EmvTransactionRecord>? = null
) : Comparable<Application>, Serializable {

    override fun compareTo(other: Application): Int {
        return priority - other.priority
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Application) return false

        return aid?.contentEquals(other.aid) ?: (other.aid == null) &&
                readingStep == other.readingStep &&
                applicationLabel == other.applicationLabel &&
                transactionCounter == other.transactionCounter &&
                leftPinTry == other.leftPinTry &&
                priority == other.priority &&
                amount == other.amount &&
                listTransactions == other.listTransactions
    }

    override fun hashCode(): Int {
        var result = aid?.contentHashCode() ?: 0
        result = 31 * result + readingStep.hashCode()
        result = 31 * result + (applicationLabel?.hashCode() ?: 0)
        result = 31 * result + transactionCounter
        result = 31 * result + leftPinTry
        result = 31 * result + priority
        result = 31 * result + amount.hashCode()
        result = 31 * result + (listTransactions?.hashCode() ?: 0)
        return result
    }
}
