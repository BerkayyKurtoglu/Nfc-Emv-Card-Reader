package com.berkay.nfcemvcardreader.emvreader.utils

import com.berkay.nfcemvcardreader.emvreader.enums.CommandEnum

/**
 * Represents an APDU command with CLA, INS, P1, P2, data, and expected response length.
 */
class CommandApdu {

    private var cla: Int = 0x00
    private var ins: Int = 0x00
    private var p1: Int = 0x00
    private var p2: Int = 0x00
    private var lc: Int = 0x00
    private var data: ByteArray = ByteArray(0)
    private var le: Int = 0x00
    private var leUsed: Boolean = false

    constructor(command: CommandEnum, data: ByteArray?, le: Int) {
        this.cla = command.cla
        this.ins = command.ins
        this.p1 = command.p1
        this.p2 = command.p2
        this.lc = data?.size ?: 0
        this.data = data ?: ByteArray(0)
        this.le = le
        this.leUsed = true
    }

    constructor(command: CommandEnum, p1: Int, p2: Int, le: Int) {
        this.cla = command.cla
        this.ins = command.ins
        this.p1 = p1
        this.p2 = p2
        this.le = le
        this.leUsed = true
    }

    constructor(command: CommandEnum, p1: Int, p2: Int) {
        this.cla = command.cla
        this.ins = command.ins
        this.p1 = p1
        this.p2 = p2
        this.leUsed = false
    }

    constructor(command: CommandEnum, p1: Int, p2: Int, data: ByteArray?, le: Int) {
        this.cla = command.cla
        this.ins = command.ins
        this.p1 = p1
        this.p2 = p2
        this.lc = data?.size ?: 0
        this.data = data ?: ByteArray(0)
        this.le = le
        this.leUsed = true
    }

    /**
     * Converts the command APDU to a byte array according to the ISO7816 specification.
     */
    fun toBytes(): ByteArray {
        var length = 4 // CLA, INS, P1, P2
        if (data.isNotEmpty()) {
            length += 1 // LC
            length += data.size // DATA
        }
        if (leUsed) {
            length += 1 // LE
        }

        val apdu = ByteArray(length)
        var index = 0
        apdu[index++] = cla.toByte()
        apdu[index++] = ins.toByte()
        apdu[index++] = p1.toByte()
        apdu[index++] = p2.toByte()

        if (data.isNotEmpty()) {
            apdu[index++] = lc.toByte()
            System.arraycopy(data, 0, apdu, index, data.size)
            index += data.size
        }
        if (leUsed) {
            apdu[index] = (apdu[index] + le.toByte()).toByte()
        }

        return apdu
    }
}
