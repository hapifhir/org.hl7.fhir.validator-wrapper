package model

import constants.Snomed
import kotlinx.serialization.Serializable


@Serializable
actual class ValidationContext actual constructor() {

    private var baseEngine: String? = null
    private var extensions : List<String> = listOf()
    private var doNative = false
    private var hintAboutNonMustSupport = false
    private var assumeValidRestReferences = false
    private var noExtensibleBindingMessages = false
    private var showTimes = false
    private var allowExampleUrls = false

    private var txServer = "qr4/"
    private var txLog: String = ""
    private var txCache: String = ""
    private var snomedCT = Snomed.INTL.code
    private var targetVer: String = ""
    private var sv: String = "5.0.0"

    private var igs: List<String> = listOf()
    private var profiles: List<String> = listOf()

    private var checkIPSCodes = false

    private var bundleValidationRules : List<BundleValidationRule> = listOf()

    private var locale: String = "en"

    constructor (validationContext : ValidationContext) : this() {
        this.igs = validationContext.igs.toList()
        this.baseEngine = validationContext.baseEngine
        this.extensions = validationContext.extensions.toList()
        this.doNative = validationContext.doNative
        this.hintAboutNonMustSupport = validationContext.hintAboutNonMustSupport
        this.assumeValidRestReferences = validationContext.assumeValidRestReferences
        this.noExtensibleBindingMessages = validationContext.noExtensibleBindingMessages
        this.showTimes = validationContext.showTimes
        this.allowExampleUrls = validationContext.allowExampleUrls
        this.txServer = validationContext.txServer
        this.txLog = validationContext.txLog
        this.txCache = validationContext.txCache
        this.snomedCT = validationContext.snomedCT
        this.targetVer = validationContext.targetVer
        this.sv = validationContext.sv
        this.profiles = validationContext.profiles.toList()
        this.checkIPSCodes = validationContext.checkIPSCodes
        this.bundleValidationRules = validationContext.bundleValidationRules.toList()
        this.locale = validationContext.locale
    }

    actual fun getBaseEngine() : String? {
        return baseEngine;
    }

    actual fun setBaseEngine(baseEngine: String?): ValidationContext {
        this.baseEngine = baseEngine
        return this
    }

    actual fun isDoNative(): Boolean {
        return doNative
    }

    actual fun setDoNative(doNative: Boolean): ValidationContext {
        this.doNative = doNative
        return this
    }

    actual fun isHintAboutNonMustSupport(): Boolean {
        return hintAboutNonMustSupport
    }

    actual fun setHintAboutNonMustSupport(hintAboutNonMustSupport: Boolean): ValidationContext {
        this.hintAboutNonMustSupport = hintAboutNonMustSupport
        return this
    }

    actual fun isAssumeValidRestReferences(): Boolean {
        return assumeValidRestReferences
    }

    actual fun setAssumeValidRestReferences(assumeValidRestReferences: Boolean): ValidationContext {
        this.assumeValidRestReferences = assumeValidRestReferences
        return this
    }

    actual fun isNoExtensibleBindingMessages(): Boolean {
        return noExtensibleBindingMessages
    }

    actual fun setNoExtensibleBindingMessages(noExtensibleBindingMessages: Boolean): ValidationContext {
        this.noExtensibleBindingMessages = noExtensibleBindingMessages
        return this
    }

    actual fun isShowTimes(): Boolean {
        return showTimes
    }

    actual fun setShowTimes(showTimes: Boolean) {
        this.showTimes = showTimes
    }

    actual fun isAllowExampleUrls(): Boolean {
        return allowExampleUrls
    }

    actual fun setAllowExampleUrls(allowExampleUrls : Boolean) {
        this.allowExampleUrls = allowExampleUrls
    }

    actual fun getTxServer(): String {
        return txServer
    }

    actual fun setTxServer(txServer: String): ValidationContext {
        this.txServer = txServer
        return this
    }

    actual fun getTxLog(): String {
        return txLog
    }

    actual fun setTxLog(txLog: String): ValidationContext {
        this.txLog = txLog
        return this
    }

    actual fun getTxCache(): String {
        return txCache
    }

    actual fun setTxCache(txCache: String): ValidationContext {
        this.txCache = txCache
        return this
    }

    actual fun getSnomedCTCode(): String {
        return snomedCT
    }

    actual fun setSnomedCT(snomedCT: String): ValidationContext {
        this.snomedCT = snomedCT
        return this
    }

    actual fun getTargetVer(): String {
        return targetVer
    }

    actual fun getSv(): String {
        return sv
    }

    actual fun setSv(sv: String): ValidationContext {
        this.sv = sv
        return this
    }

    actual fun setTargetVer(targetVer: String): ValidationContext {
        this.targetVer = targetVer
        return this
    }

    actual fun getIgs(): List<String> {
        return igs
    }

    actual fun setIgs(igs: List<String>): ValidationContext {
        this.igs = igs
        return this
    }

   actual fun addIg(ig: String): ValidationContext {
        this.igs += ig
        return this
    }

    fun removeIg(ig: String): ValidationContext {
        if (this.igs.contains(ig)) {
            this.igs = this.igs.filterNot { it == ig }.toList()
        }
        return this
    }

    actual fun getProfiles(): List<String> {
        return profiles
    }

    actual fun setProfiles(profiles: List<String>): ValidationContext {
        this.profiles = profiles
        return this
    }

    fun addProfile(profile: String): ValidationContext {
        this.profiles += profile
        return this
    }

    fun removeProfile(profile: String): ValidationContext {
        if (this.profiles.contains(profile)) {
            this.profiles = this.profiles.filterNot { it == profile }.toList()
        }
        return this
    }

    actual fun setLocale(languageString: String): ValidationContext {
        this.locale = languageString
        return this
    }


    actual fun getLanguageCode() : String {
        return this.locale
    }

    fun prettyPrint() {
        println("ValidationContext :: \n" +
                "doNative = ${doNative}\n" +
                "hintAboutNonMustSupport = ${hintAboutNonMustSupport}\n" +
                "assumeValidRestReferences = ${assumeValidRestReferences}\n" +
                "noExtensibleBindingMessages = ${noExtensibleBindingMessages}\n" +
                "showTimes = ${showTimes}\n" +
                "txServer = ${txServer}\n" +
                "txLog = ${txLog}\n" +
                "txCache = ${txCache}\n" +
                "snomedCT = ${snomedCT}\n" +
                "targetVer = ${targetVer}\n" +
                "locale = ${locale}\n" +
                "igs -> \n" +
                igs.forEach { println("\t" + it) }
        )
    }

    actual fun setExtensions(extensions : List<String>) : ValidationContext {
        this.extensions = extensions
        return this
    }

    actual fun getExtensions() : List<String> {
        return this.extensions
    }

    actual fun setCheckIPSCodes(checkIPSCodes: Boolean): ValidationContext {
        this.checkIPSCodes = checkIPSCodes;
        return this;
    }

    actual fun isCheckIPSCodes(): Boolean {
        return this.checkIPSCodes
    }

    actual fun getBundleValidationRules(): List<BundleValidationRule> {
        return bundleValidationRules
    }

    actual fun setBundleValidationRules(bundleValidationRules: List<BundleValidationRule>): ValidationContext {
        this.bundleValidationRules = bundleValidationRules
        return this
    }


}