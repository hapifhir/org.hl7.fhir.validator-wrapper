package model


expect class ValidationEngineSettings() {
    fun isDoNative(): Boolean
    fun setDoNative(doNative: Boolean): ValidationEngineSettings
}