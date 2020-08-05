package model

import kotlinx.serialization.Serializable

@Serializable
actual class ValidationOutcome actual constructor() {

    private var fileInfo: FileInfo = FileInfo()
    private var issues: List<ValidationIssue> = listOf()

    constructor(fileInfo: FileInfo, issues: List<ValidationIssue>) : this() {
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

    actual fun getIssues(): List<ValidationIssue> {
        return issues
    }

    actual fun setIssues(issues: List<ValidationIssue>): ValidationOutcome {
        this.issues = issues
        return this
    }
}