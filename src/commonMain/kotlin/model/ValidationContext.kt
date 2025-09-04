package model

expect class ValidationContext() {

    fun getBaseEngine(): String?
    fun setBaseEngine(baseEngine: String?) : ValidationContext

    fun isDoNative(): Boolean
    fun setDoNative(doNative: Boolean): ValidationContext

    fun isHintAboutNonMustSupport(): Boolean
    fun setHintAboutNonMustSupport(hintAboutNonMustSupport: Boolean): ValidationContext

    fun isAssumeValidRestReferences(): Boolean
    fun setAssumeValidRestReferences(assumeValidRestReferences: Boolean): ValidationContext

    fun isNoExtensibleBindingMessages(): Boolean
    fun setNoExtensibleBindingMessages(noExtensibleBindingMessages: Boolean): ValidationContext

    fun isShowTimes(): Boolean
    fun setShowTimes(showTimes: Boolean)

    fun getTxServer(): String
    fun setTxServer(txServer: String): ValidationContext

    fun getTxLog(): String
    fun setTxLog(txLog: String): ValidationContext

    fun getTxCache(): String
    fun setTxCache(txCache: String): ValidationContext

    fun getSnomedCTCode(): String
    fun setSnomedCT(snomedCT: String): ValidationContext

    fun getTargetVer(): String
    fun setTargetVer(targetVer: String): ValidationContext

    fun getIgs(): List<String>
    fun setIgs(igs: List<String>): ValidationContext

    fun getProfiles(): List<String>
    fun setProfiles(profiles: List<String>): ValidationContext
//TODO
//    fun getQuestionnaires(): List<String>
//    fun setQuestionnaires(questionnaires: List<String>): ValidationContext

    fun getSv(): String
    fun setSv(sv: String): ValidationContext

    fun isAllowExampleUrls() : Boolean
    fun setAllowExampleUrls(allowExampleUrls:Boolean)

    fun setLocale(languageString: String) : ValidationContext
    fun getLanguageCode() : String

    fun setExtensions(extensions: List<String>) : ValidationContext
    fun getExtensions() : List<String>

    fun setCheckIPSCodes(checkIPSCodes : Boolean) : ValidationContext
    fun isCheckIPSCodes() : Boolean

    fun setBundleValidationRules(bundleValidationRules: List<BundleValidationRule>) : ValidationContext
    fun getBundleValidationRules():List<BundleValidationRule>

    fun addIg(ig: String): ValidationContext

}