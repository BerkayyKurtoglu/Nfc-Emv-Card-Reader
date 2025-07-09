package com.berkay.nfcemvcardreader.emvreader.parser.apdu.impl

import android.util.Log
import com.berkay.nfcemvcardreader.emvreader.iso7816emv.TagAndLength
import com.berkay.nfcemvcardreader.emvreader.parser.apdu.IFile
import com.berkay.nfcemvcardreader.emvreader.parser.apdu.annotation.AnnotationData
import com.berkay.nfcemvcardreader.emvreader.parser.apdu.annotation.AnnotationUtils
import com.berkay.nfcemvcardreader.emvreader.utils.bitutils.BitUtils
import java.lang.reflect.Field

/**
 * Base class for parsing byte[] data into fields using @Data annotations.
 * Designed for EMV card record parsing.
 */
abstract class AbstractByteBean<T> : IFile {

    companion object {
        private const val TAG = "AbstractByteBean"
    }

    /**
     * Returns a set of annotation metadata from this class, optionally based on a given tag list.
     */
    private fun getAnnotationSet(tags: Collection<TagAndLength>?): Collection<AnnotationData> {
        return if (tags != null) {
            val map = AnnotationUtils.map[this::class.java.name]
            val result = ArrayList<AnnotationData>(tags.size)
            for (tagAndLength in tags) {
                val ann = map?.get(tagAndLength.tag)?.apply {
                    size = tagAndLength.length * BitUtils.BYTE_SIZE
                } ?: AnnotationData().apply {
                    skip = true
                    size = tagAndLength.length * BitUtils.BYTE_SIZE
                }
                result.add(ann)
            }
            result
        } else {
            AnnotationUtils.mapSet[this::class.java.name] ?: emptyList()
        }
    }

    /**
     * Parses raw byte data into fields of the current class using annotation metadata.
     */
    override fun parse(data: ByteArray, list: Collection<TagAndLength>) {
        val annotations = getAnnotationSet(list)
        val bitUtils = BitUtils(data)
        for (annotation in annotations) {
            if (annotation.skip) {
                bitUtils.addCurrentBitIndex(annotation.size)
            } else {
                val value = DataFactory.getObject(annotation, bitUtils)
                setField(annotation.field, this, value)
            }
        }
    }

    /**
     * Reflectively sets a field with the provided value.
     */
    protected fun setField(field: Field?, target: IFile, value: Any?) {
        if (field != null) {
            try {
                field.set(target, value)
            } catch (e: IllegalArgumentException) {
                Log.e(TAG, "Invalid parameters passed to field.set", e)
            } catch (e: IllegalAccessException) {
                Log.e(TAG, "Failed to set field: ${field.name}", e)
            }
        }
    }
}
