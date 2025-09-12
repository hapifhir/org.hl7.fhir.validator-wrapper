package model

import constants.Snomed
import kotlinx.serialization.Serializable

@Serializable
actual class ValidationEngineSettings actual constructor() {

    private var baseEngine: String? = null

    actual fun getBaseEngine(): String? {
        return baseEngine
    }

    actual fun setBaseEngine(baseEngine: String?): ValidationEngineSettings {
        this.baseEngine = baseEngine
        return this
    }

    private var doNative = false

    actual fun isDoNative(): Boolean {
        return doNative
    }

    actual fun setDoNative(doNative: Boolean): ValidationEngineSettings {
        this.doNative = doNative
        return this
    }

    private var snomedCT = Snomed.INTL.code

    actual fun getSnomedCTCode(): String {
        return snomedCT
    }

    actual fun setSnomedCT(snomedCT: String): ValidationEngineSettings {
        this.snomedCT = snomedCT
        return this
    }

    private var hintAboutNonMustSupport = false

    actual fun isHintAboutNonMustSupport(): Boolean {
        return hintAboutNonMustSupport
    }

    actual fun setHintAboutNonMustSupport(hintAboutNonMustSupport: Boolean): ValidationEngineSettings {
        this.hintAboutNonMustSupport = hintAboutNonMustSupport
        return this
    }

    private var assumeValidRestReferences = false

    actual fun isAssumeValidRestReferences(): Boolean {
        return assumeValidRestReferences
    }

    actual fun setAssumeValidRestReferences(assumeValidRestReferences: Boolean): ValidationEngineSettings {
        this.assumeValidRestReferences = assumeValidRestReferences
        return this
    }

    private var noExtensibleBindingMessages = false

    actual fun isNoExtensibleBindingMessages(): Boolean {
        return noExtensibleBindingMessages
    }

    actual fun setNoExtensibleBindingMessages(noExtensibleBindingMessages: Boolean): ValidationEngineSettings {
        this.noExtensibleBindingMessages = noExtensibleBindingMessages
        return this
    }

    constructor (validationEngineSettings : ValidationEngineSettings) : this() {
        this.baseEngine = validationEngineSettings.baseEngine
        this.doNative = validationEngineSettings.doNative
        this.snomedCT = validationEngineSettings.snomedCT
        this.hintAboutNonMustSupport = validationEngineSettings.hintAboutNonMustSupport
        this.assumeValidRestReferences = validationEngineSettings.assumeValidRestReferences
        this.noExtensibleBindingMessages = validationEngineSettings.noExtensibleBindingMessages
    }

}