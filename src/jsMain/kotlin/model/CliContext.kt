package model

import constants.Snomed
import kotlinx.serialization.Serializable

@Serializable
actual class CliContext actual constructor() {

    private var doNative = false
    private var hintAboutNonMustSupport = false
    private var assumeValidRestReferences = false
    private var noExtensibleBindingMessages = false
    private var showTimes = false

    private var txServer = "http://tx.fhir.org"
    private var txLog: String = ""
    private var snomedCT = Snomed.INTL.code
    private var targetVer: String = "4.5.0"

    private var igs: List<String> = listOf()

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

    fun addIg(ig: String): CliContext {
        this.igs += ig
        return this
    }

    fun removeIg(ig: String): CliContext {
        if (this.igs.contains(ig)) {
            this.igs = this.igs.filterNot { it == ig }.toList()
        }
        return this
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
                "snomedCT = ${snomedCT}\n" +
                "targetVer = ${targetVer}\n" +
                "igs -> \n" +
                igs.forEach { println("\t" + it) }
        )
    }

}