package model

import constants.Snomed
import kotlinx.serialization.Serializable

@Serializable
actual class ValidationEngineSettings actual constructor() {

    private var baseEngine: String? = null
    private var doNative = false

    actual fun isDoNative(): Boolean {
        return doNative
    }

    actual fun setDoNative(doNative: Boolean): ValidationEngineSettings {
        this.doNative = doNative
        return this
    }

    actual fun getBaseEngine(): String? {
       return baseEngine
    }

    actual fun setBaseEngine(baseEngine: String?): ValidationEngineSettings {
        this.baseEngine = baseEngine
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

    constructor (validationEngineSettings : ValidationEngineSettings) : this() {
        this.baseEngine = validationEngineSettings.baseEngine
        this.doNative = validationEngineSettings.doNative
    }
}