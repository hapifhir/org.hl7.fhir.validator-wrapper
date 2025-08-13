package model

import kotlinx.serialization.Serializable

@Serializable
actual class ValidationRequest actual constructor() {

    private var validationEngineSettings: ValidationEngineSettings = ValidationEngineSettings()
    private var validationContext: ValidationContext = ValidationContext()
    private var filesToValidate: List<FileInfo> = listOf()
    private var sessionId: String = ""

    constructor(validationEngineSettings: ValidationEngineSettings, validationContext: ValidationContext, filesToValidate: List<FileInfo>) : this() {
        this.validationEngineSettings = validationEngineSettings
        this.validationContext = validationContext
        this.filesToValidate = filesToValidate
    }

    actual fun getValidationEngineSettings(): ValidationEngineSettings = validationEngineSettings
    actual fun setValidationEngineSettings(validationEngineSettings: ValidationEngineSettings): ValidationRequest {
        this.validationEngineSettings = validationEngineSettings
        return this
    }

    actual fun getValidationContext(): ValidationContext {
        return validationContext
    }

    actual fun setValidationContext(validationContext: ValidationContext): ValidationRequest {
        this.validationContext = validationContext
        return this
    }

    actual fun setCliContext(validationContext: ValidationContext): ValidationRequest {
        this.validationContext = validationContext
        return this
    }

    actual fun getFilesToValidate(): List<FileInfo> {
        return filesToValidate
    }

    actual fun setFilesToValidate(filesToValidate: List<FileInfo>): ValidationRequest {
        this.filesToValidate = filesToValidate
        return this
    }

    actual fun getSessionId(): String {
        return sessionId
    }

    actual fun setSessionId(sessionId: String): ValidationRequest {
        this.sessionId = sessionId
        return this
    }

}