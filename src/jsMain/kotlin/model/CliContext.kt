package model

import constants.Snomed
import kotlinx.serialization.Serializable

@Serializable
actual class CliContext actual constructor() {

    private var doNative = false
    private var hintAboutNonMustSupport = false
    private var assumeValidRestReferences = false
    private var noExtensibleBindingMessages = false
    private var securityChecks = false
    private var showTimes = false

//    private var txServer = "http://tx.fhir.org"
//    private var txLog: String = ""
//    private var snomedCT = Snomed.INTL.code
//    private var targetVer: String = ""
//
//    private var igs: List<String> = listOf()
//    private var questionnaires: List<String> = listOf()

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

    actual fun isSecurityChecks(): Boolean {
        return securityChecks
    }

    actual fun setSecurityChecks(securityChecks: Boolean): CliContext {
        this.securityChecks = securityChecks
        return this
    }

    actual fun isShowTimes(): Boolean {
        return showTimes
    }

    actual fun setShowTimes(showTimes: Boolean) {
        this.showTimes = showTimes
    }

//    actual fun getTxServer(): String {
//        return txServer
//    }
//
//    actual fun setTxServer(txServer: String): CliContext {
//        this.txServer = txServer
//        return this
//    }
//
//    actual fun getTxLog(): String {
//        return txLog
//    }
//
//    actual fun setTxLog(txLog: String): CliContext {
//        this.txLog = txLog
//        return this
//    }
//
//    actual fun getSnomedCTCode(): String {
//        return snomedCT
//    }
//
//    actual fun setSnomedCT(snomedCT: String): CliContext {
//        this.snomedCT = snomedCT
//        return this
//    }
//
//    actual fun getTargetVer(): String {
//        return targetVer
//    }
//
//    actual fun setTargetVer(targetVer: String): CliContext {
//        this.targetVer = targetVer
//        return this
//    }
//
//    actual fun getIgs(): List<String> {
//        return igs
//    }
//
//    actual fun setIgs(igs: List<String>): CliContext {
//        this.igs = igs
//        return this
//    }
//
//    actual fun getQuestionnaires(): List<String> {
//        return questionnaires
//    }
//
//    actual fun setQuestionnaires(questionnaires: List<String>): CliContext {
//        this.questionnaires = questionnaires
//        return this
//    }

}