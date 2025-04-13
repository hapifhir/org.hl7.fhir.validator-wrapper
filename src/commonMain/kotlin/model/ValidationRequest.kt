package model

expect class ValidationRequest() {
    fun getValidationContext(): ValidationContext
    fun setValidationContext(validationContext: ValidationContext): ValidationRequest
    fun getFilesToValidate(): List<FileInfo>
    fun setFilesToValidate(filesToValidate: List<FileInfo>): ValidationRequest
    fun getSessionId(): String
    fun setSessionId(sessionId: String): ValidationRequest
}
