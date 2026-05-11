package model

// org.hl7.fhir.* deprecation is intentional pending upstream API updates.
// Kotlin also substitutes ValidationContext in common code triggering
//   TYPEALIAS_EXPANSION_DEPRECATION.
@Suppress("TYPEALIAS_EXPANSION_DEPRECATION")
expect class ValidationRequest() {
    fun setCliContext(validationContext: ValidationContext): ValidationRequest
    fun getValidationContext(): ValidationContext
    fun setValidationContext(validationContext: ValidationContext): ValidationRequest
    fun getFilesToValidate(): List<FileInfo>
    fun setFilesToValidate(filesToValidate: List<FileInfo>): ValidationRequest
    fun getSessionId(): String
    fun setSessionId(sessionId: String): ValidationRequest
}
