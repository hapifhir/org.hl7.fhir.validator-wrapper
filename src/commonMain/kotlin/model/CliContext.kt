package model

expect class CliContext() {

    fun isDoNative(): Boolean
    fun setDoNative(doNative: Boolean): CliContext

    fun isHintAboutNonMustSupport(): Boolean
    fun setHintAboutNonMustSupport(hintAboutNonMustSupport: Boolean): CliContext

    fun isAssumeValidRestReferences(): Boolean
    fun setAssumeValidRestReferences(assumeValidRestReferences: Boolean): CliContext

    fun isNoExtensibleBindingMessages(): Boolean
    fun setNoExtensibleBindingMessages(noExtensibleBindingMessages: Boolean): CliContext

    fun isShowTimes(): Boolean
    fun setShowTimes(showTimes: Boolean)

    fun getTxServer(): String
    fun setTxServer(txServer: String): CliContext

    fun getTxLog(): String
    fun setTxLog(txLog: String): CliContext

    fun getSnomedCTCode(): String
    fun setSnomedCT(snomedCT: String): CliContext

    fun getTargetVer(): String
    fun setTargetVer(targetVer: String): CliContext

    fun getIgs(): List<String>
    fun setIgs(igs: List<String>): CliContext
//TODO
//    fun getQuestionnaires(): List<String>
//    fun setQuestionnaires(questionnaires: List<String>): CliContext

    fun getSv(): String
    fun setSv(sv: String): CliContext
}