package model


expect class ValidationEngineSettings() {

    fun getBaseEngine(): String?
    fun setBaseEngine(baseEngine:String?) : ValidationEngineSettings

    fun isDoNative(): Boolean
    fun setDoNative(doNative: Boolean): ValidationEngineSettings


}