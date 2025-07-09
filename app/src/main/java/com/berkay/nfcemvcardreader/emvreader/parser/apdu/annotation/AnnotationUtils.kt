package com.berkay.nfcemvcardreader.emvreader.parser.apdu.annotation

import com.berkay.nfcemvcardreader.emvreader.iso7816emv.tag.ITag
import com.berkay.nfcemvcardreader.emvreader.model.CPLC
import com.berkay.nfcemvcardreader.emvreader.model.EmvTransactionRecord
import com.berkay.nfcemvcardreader.emvreader.parser.apdu.IFile

/**
 * Singleton utility for processing @Data annotations on IFile implementations.
 */
object AnnotationUtils {

    private val listeClass: Array<Class<out IFile>> = arrayOf(
        EmvTransactionRecord::class.java,
        CPLC::class.java
    )

    val map: MutableMap<String, MutableMap<ITag, AnnotationData>> = HashMap()
    val mapSet: MutableMap<String, MutableSet<AnnotationData>> = HashMap()

    init {
        extractAnnotation()
    }

    /**
     * Extracts and indexes all @Data annotations from supported classes.
     */
    private fun extractAnnotation() {
        for (clazz in listeClass) {
            val maps = mutableMapOf<ITag, AnnotationData>()
            val set = sortedSetOf<AnnotationData>()

            for (field in clazz.declaredFields) {
                field.isAccessible = true
                val annotation = field.getAnnotation(Data::class.java)
                if (annotation != null) {
                    val param = AnnotationData()
                    param.field = field
                    param.initFromAnnotation(annotation)
                    param.tag?.let { maps[it] = param }
                    set.add(param.clone())
                }
            }

            mapSet[clazz.name] = set
            map[clazz.name] = maps
        }
    }
}
