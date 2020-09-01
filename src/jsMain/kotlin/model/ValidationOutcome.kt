package model

import kotlinx.serialization.Serializable

@Serializable
actual class ValidationOutcome actual constructor() {

    private var fileInfo: FileInfo = FileInfo()
    private var issues: List<ValidationMessage> = listOf()

    constructor(fileInfo: FileInfo, issues: List<ValidationMessage>) : this() {
        this.fileInfo = fileInfo
        this.issues = issues
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
}