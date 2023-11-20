package model

expect class ValidationTime() {

    fun getTxTime(): Long
    fun setTxTime(txTime: Long): ValidationTime
    fun getSdTime(): Long
    fun setSdTime(sdTime: Long): ValidationTime
    fun getLoadTime(): Long
    fun setLoadTime(loadTime: Long): ValidationTime
    fun getFhirPath(): Long
    fun setFhirPath(fhirPath: Long): ValidationTime
    fun getSpecTime(): Long
    fun setSpecTime(specTime: Long): ValidationTime
    fun getOverall(): Long
    fun setOverall(overall: Long): ValidationTime
}