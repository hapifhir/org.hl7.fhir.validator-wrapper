package model

import constants.Snomed
import kotlinx.serialization.Serializable


@Serializable
actual class CliContext actual constructor() {

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
    private var sv: String = ""

    private var igs: List<String> = listOf()
    private var profiles: List<String> = listOf()

    private var checkIPSCodes = false

    private var bundleValidationRules : List<BundleValidationRule> = listOf()

    private var locale: String = ""
    init {
        sv = "5.0.0"
        locale = "en"
    }

    constructor (cliContext : CliContext) : this() {
        this.igs = cliContext.igs.toList()
        this.baseEngine = cliContext.baseEngine
        this.extensions = cliContext.extensions.toList()
        this.doNative = cliContext.doNative
        this.hintAboutNonMustSupport = cliContext.hintAboutNonMustSupport
        this.assumeValidRestReferences = cliContext.assumeValidRestReferences
        this.noExtensibleBindingMessages = cliContext.noExtensibleBindingMessages
        this.showTimes = cliContext.showTimes
        this.allowExampleUrls = cliContext.allowExampleUrls
        this.txServer = cliContext.txServer
        this.txLog = cliContext.txLog
        this.txCache = cliContext.txCache
        this.snomedCT = cliContext.snomedCT
        this.targetVer = cliContext.targetVer
        this.sv = cliContext.sv
        this.profiles = cliContext.profiles.toList()
        this.checkIPSCodes = cliContext.checkIPSCodes
        this.bundleValidationRules = cliContext.bundleValidationRules.toList()
        this.locale = cliContext.locale
    }

    actual fun getBaseEngine() : String? {
        return baseEngine;
    }

    actual fun setBaseEngine(baseEngine: String?): CliContext {
        this.baseEngine = baseEngine
        return this
    }

    actual fun isDoNative(): Boolean {
        return doNative
    }

    actual fun setDoNative(doNative: Boolean): CliContext {
        this.doNative = doNative
        return this
    }

    actual fun isHintAboutNonMustSupport(): Boolean {
        return hintAboutNonMustSupport
    }

    actual fun setHintAboutNonMustSupport(hintAboutNonMustSupport: Boolean): CliContext {
        this.hintAboutNonMustSupport = hintAboutNonMustSupport
        return this
    }

    actual fun isAssumeValidRestReferences(): Boolean {
        return assumeValidRestReferences
    }

    actual fun setAssumeValidRestReferences(assumeValidRestReferences: Boolean): CliContext {
        this.assumeValidRestReferences = assumeValidRestReferences
        return this
    }

    actual fun isNoExtensibleBindingMessages(): Boolean {
        return noExtensibleBindingMessages
    }

    actual fun setNoExtensibleBindingMessages(noExtensibleBindingMessages: Boolean): CliContext {
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

    actual fun setTxServer(txServer: String): CliContext {
        this.txServer = txServer
        return this
    }

    actual fun getTxLog(): String {
        return txLog
    }

    actual fun setTxLog(txLog: String): CliContext {
        this.txLog = txLog
        return this
    }

    actual fun getTxCache(): String {
        return txCache
    }

    actual fun setTxCache(txCache: String): CliContext {
        this.txCache = txCache
        return this
    }

    actual fun getSnomedCTCode(): String {
        return snomedCT
    }

    actual fun setSnomedCT(snomedCT: String): CliContext {
        this.snomedCT = snomedCT
        return this
    }

    actual fun getTargetVer(): String {
        return targetVer
    }

    actual fun getSv(): String {
        return sv
    }

    actual fun setSv(sv: String): CliContext {
        this.sv = sv
        return this
    }

    actual fun setTargetVer(targetVer: String): CliContext {
        this.targetVer = targetVer
        return this
    }

    actual fun getIgs(): List<String> {
        return igs
    }

    actual fun setIgs(igs: List<String>): CliContext {
        this.igs = igs
        return this
    }

   actual fun addIg(ig: String): CliContext {
        this.igs += ig
        return this
    }

    fun removeIg(ig: String): CliContext {
        if (this.igs.contains(ig)) {
            this.igs = this.igs.filterNot { it == ig }.toList()
        }
        return this
    }

    actual fun getProfiles(): List<String> {
        return profiles
    }

    actual fun setProfiles(profiles: List<String>): CliContext {
        this.profiles = profiles
        return this
    }

    fun addProfile(profile: String): CliContext {
        this.profiles += profile
        return this
    }

    fun removeProfile(profile: String): CliContext {
        if (this.profiles.contains(profile)) {
            this.profiles = this.profiles.filterNot { it == profile }.toList()
        }
        return this
    }

    actual fun setLocale(languageString: String): CliContext {
        this.locale = languageString
        return this
    }


    actual fun getLanguageCode() : String {
        return this.locale
    }

    fun prettyPrint() {
        println("CliContext :: \n" +
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

    actual fun setExtensions(extensions : List<String>) : CliContext {
        this.extensions = extensions
        return this
    }

    actual fun getExtensions() : List<String> {
        return this.extensions
    }

    actual fun setCheckIPSCodes(checkIPSCodes: Boolean): CliContext {
        this.checkIPSCodes = checkIPSCodes;
        return this;
    }

    actual fun isCheckIPSCodes(): Boolean {
        return this.checkIPSCodes
    }

    actual fun getBundleValidationRules(): List<BundleValidationRule> {
        return bundleValidationRules
    }

    actual fun setBundleValidationRules(bundleValidationRules: List<BundleValidationRule>): CliContext {
        this.bundleValidationRules = bundleValidationRules
        return this
    }


}