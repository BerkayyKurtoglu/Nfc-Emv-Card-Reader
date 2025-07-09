package com.berkay.nfcemvcardreader.emvreader.model

import com.berkay.nfcemvcardreader.emvreader.parser.apdu.annotation.Data
import com.berkay.nfcemvcardreader.emvreader.parser.apdu.impl.AbstractByteBean
import com.berkay.nfcemvcardreader.emvreader.parser.apdu.impl.DataFactory
import java.io.Serializable
import java.util.Date

/**
 * Represents Card Production Life-Cycle (CPLC) information as defined by GlobalPlatform.
 * This immutable data block includes chip manufacturing and personalization metadata.
 */
data class CPLC(

    @Data(index = 1, size = 16)
    var icFabricator: Int? = null,

    @Data(index = 2, size = 16)
    var icType: Int? = null,

    @Data(index = 3, size = 16)
    var os: Int? = null,

    @Data(index = 4, size = 16, dateStandard = DataFactory.CPCL_DATE)
    var osReleaseDate: Date? = null,

    @Data(index = 5, size = 16)
    var osReleaseLevel: Int? = null,

    @Data(index = 6, size = 16, dateStandard = DataFactory.CPCL_DATE)
    var icFabricDate: Date? = null,

    @Data(index = 7, size = 32)
    var icSerialNumber: Int? = null,

    @Data(index = 8, size = 16)
    var icBatchId: Int? = null,

    @Data(index = 9, size = 16)
    var icModuleFabricator: Int? = null,

    @Data(index = 10, size = 16, dateStandard = DataFactory.CPCL_DATE)
    var icPackagingDate: Date? = null,

    @Data(index = 11, size = 16)
    var iccManufacturer: Int? = null,

    @Data(index = 12, size = 16, dateStandard = DataFactory.CPCL_DATE)
    var icEmbeddingDate: Date? = null,

    @Data(index = 13, size = 16)
    var prepersoId: Int? = null,

    @Data(index = 14, size = 16, dateStandard = DataFactory.CPCL_DATE)
    var prepersoDate: Date? = null,

    @Data(index = 15, size = 32)
    var prepersoEquipment: Int? = null,

    @Data(index = 16, size = 16)
    var persoId: Int? = null,

    @Data(index = 17, size = 16, dateStandard = DataFactory.CPCL_DATE)
    var persoDate: Date? = null,

    @Data(index = 18, size = 32)
    var persoEquipment: Int? = null,
) : AbstractByteBean<CPLC>(), Serializable
