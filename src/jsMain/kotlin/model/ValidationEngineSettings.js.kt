package model

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
}