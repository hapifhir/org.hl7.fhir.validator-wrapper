package model

import kotlinx.serialization.Serializable

@Serializable
actual class ValidationOutcome actual constructor() {

    private var fileInfo: FileInfo = FileInfo()
    private var issues: List<ValidationMessage> = listOf()
    private var validated: Boolean = false
    private var validating: Boolean = false

    constructor(fileInfo: FileInfo, issues: List<ValidationMessage>, validated: Boolean = false, validating: Boolean = false) : this() {
        this.fileInfo = fileInfo
        this.issues = issues
        this.validated = validated
        this.validating = validating
    }

    actual fun getFileInfo(): FileInfo {
        return fileInfo
    }

    actual fun setFileInfo(fileInfo: FileInfo): ValidationOutcome {
        this.fileInfo = fileInfo
        return this
    }

    actual fun getMessages(): List<ValidationMessage> {
        return issues
    }

    actual fun setMessages(issues: List<ValidationMessage>): ValidationOutcome {
        this.issues = issues
        return this
    }

    fun isValidated(): Boolean {
        return validated
    }

    fun setValidated(validated: Boolean): ValidationOutcome {
        this.validated = validated
        return this
    }

    fun isValidating(): Boolean {
        return validating
    }

    fun setValidating(validating: Boolean): ValidationOutcome {
        this.validating = validating
        return this
    }
}