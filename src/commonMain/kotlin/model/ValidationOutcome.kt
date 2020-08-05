package model

expect class ValidationOutcome() {
    fun getFileInfo(): FileInfo
    fun setFileInfo(fileInfo: FileInfo): ValidationOutcome
    fun getIssues(): List<ValidationIssue>
    fun setIssues(issues: List<ValidationIssue>): ValidationOutcome
}
