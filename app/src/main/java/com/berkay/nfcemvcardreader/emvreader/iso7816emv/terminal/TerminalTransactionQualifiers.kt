package com.berkay.nfcemvcardreader.emvreader.iso7816emv.terminal

import com.berkay.nfcemvcardreader.emvreader.utils.bitutils.BytesUtils


class TerminalTransactionQualifiers {

    private val data = ByteArray(4)

    fun contactlessMagneticStripeSupported(): Boolean {
        return BytesUtils.matchBitByBitIndex(data[0].toInt() and 0xFF, 7)
    }

    fun contactlessVSDCsupported(): Boolean {
        return BytesUtils.matchBitByBitIndex(data[0].toInt() and 0xFF, 6)
    }

    fun contactlessEMVmodeSupported(): Boolean {
        return BytesUtils.matchBitByBitIndex(data[0].toInt() and 0xFF, 5)
    }

    fun contactEMVsupported(): Boolean {
        return BytesUtils.matchBitByBitIndex(data[0].toInt() and 0xFF, 4)
    }

    fun readerIsOfflineOnly(): Boolean {
        return BytesUtils.matchBitByBitIndex(data[0].toInt() and 0xFF, 3)
    }

    fun onlinePINsupported(): Boolean {
        return BytesUtils.matchBitByBitIndex(data[0].toInt() and 0xFF, 2)
    }

    fun signatureSupported(): Boolean {
        return BytesUtils.matchBitByBitIndex(data[0].toInt() and 0xFF, 1)
    }

    fun onlineCryptogramRequired(): Boolean {
        return BytesUtils.matchBitByBitIndex(data[1].toInt() and 0xFF, 7)
    }

    fun cvmRequired(): Boolean {
        return BytesUtils.matchBitByBitIndex(data[1].toInt() and 0xFF, 6)
    }

    fun contactChipOfflinePINsupported(): Boolean {
        return BytesUtils.matchBitByBitIndex(data[1].toInt() and 0xFF, 5)
    }

    fun issuerUpdateProcessingSupported(): Boolean {
        return BytesUtils.matchBitByBitIndex(data[2].toInt() and 0xFF, 7)
    }

    fun consumerDeviceCVMsupported(): Boolean {
        return BytesUtils.matchBitByBitIndex(data[2].toInt() and 0xFF, 6)
    }

    // Setters similarly need to set bits in data array correctly, ensure to update those as well

    fun setMagneticStripeSupported(value: Boolean) {
        data[0] = BytesUtils.setBit(data[0], 7, value)
    }

    fun setContactlessVSDCsupported(value: Boolean) {
        data[0] = BytesUtils.setBit(data[0], 6, value)
        if (value) {
            setContactlessEMVmodeSupported(false)
        }
    }

    fun setContactlessEMVmodeSupported(value: Boolean) {
        data[0] = BytesUtils.setBit(data[0], 5, value)
    }

    fun setContactEMVsupported(value: Boolean) {
        data[0] = BytesUtils.setBit(data[0], 4, value)
    }

    fun setReaderIsOfflineOnly(value: Boolean) {
        data[0] = BytesUtils.setBit(data[0], 3, value)
    }

    fun setOnlinePINsupported(value: Boolean) {
        data[0] = BytesUtils.setBit(data[0], 2, value)
    }

    fun setSignatureSupported(value: Boolean) {
        data[0] = BytesUtils.setBit(data[0], 1, value)
    }

    fun setOnlineCryptogramRequired(value: Boolean) {
        data[1] = BytesUtils.setBit(data[1], 7, value)
    }

    fun setCvmRequired(value: Boolean) {
        data[1] = BytesUtils.setBit(data[1], 6, value)
    }

    fun setContactChipOfflinePINsupported(value: Boolean) {
        data[1] = BytesUtils.setBit(data[1], 5, value)
    }

    fun setIssuerUpdateProcessingSupported(value: Boolean) {
        data[2] = BytesUtils.setBit(data[2], 7, value)
    }

    fun setConsumerDeviceCVMsupported(value: Boolean) {
        data[2] = BytesUtils.setBit(data[2], 6, value)
    }

    fun getBytes(): ByteArray {
        return data.copyOf()
    }
}
