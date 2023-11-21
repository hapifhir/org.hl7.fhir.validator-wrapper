package model

expect class ValidationTime() {

    fun getTerminology(): Long
    fun setTerminology(terminology: Long): ValidationTime
    fun getStructureDefinition(): Long
    fun setStructureDefinition(structureDefinition: Long): ValidationTime
    fun getResourceParse(): Long
    fun setResourceParse(resourceParse: Long): ValidationTime
    fun getFhirPath(): Long
    fun setFhirPath(fhirPath: Long): ValidationTime
    fun getCheckingSpecials(): Long
    fun setCheckingSpecials(checkingSpecials: Long): ValidationTime
    fun getOverall(): Long
    fun setOverall(overall: Long): ValidationTime
}