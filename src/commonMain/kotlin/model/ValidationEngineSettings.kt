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

    fun isAssumeValidRestReferences(): Boolean
    fun setAssumeValidRestReferences(assumeValidRestReferences: Boolean): ValidationEngineSettings

    fun isNoExtensibleBindingMessages(): Boolean
    fun setNoExtensibleBindingMessages(noExtensibleBindingMessages: Boolean): ValidationEngineSettings

}