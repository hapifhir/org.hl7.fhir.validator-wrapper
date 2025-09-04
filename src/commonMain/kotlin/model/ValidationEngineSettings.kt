package model


expect class ValidationEngineSettings() {

    fun getBaseEngine(): String?
    fun setBaseEngine(baseEngine:String?) : ValidationEngineSettings

    fun isDoNative(): Boolean
    fun setDoNative(doNative: Boolean): ValidationEngineSettings

    fun getSnomedCTCode(): String
    fun setSnomedCT(snomedCT: String): ValidationEngineSettings

    fun isHintAboutNonMustSupport(): Boolean
    fun setHintAboutNonMustSupport(hintAboutNonMustSupport: Boolean): ValidationEngineSettings

}