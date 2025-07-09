package com.berkay.nfcemvcardreader.emvreader.model.enums

/**
 * Represents the current step of an EMV application during reading.
 */
enum class ApplicationStepEnum(private val key: Int) : IKeyEnum {

    /**
     * Application has not been selected yet.
     */
    NOT_SELECTED(0),

    /**
     * Application has been selected.
     */
    SELECTED(1),

    /**
     * Application has been fully read.
     */
    READ(2);

    override fun getKey(): Int = key

    companion object {
//
//        /**
//         * Returns true if any application has reached at least the specified step.
//         */
//        fun isAtLeast(applications: List<Application>?, step: ApplicationStepEnum?): Boolean {
//            if (applications == null || step == null) return false
//            return applications.any { it.readingStep.getKey() >= step.getKey() }
//        }
    }
}
