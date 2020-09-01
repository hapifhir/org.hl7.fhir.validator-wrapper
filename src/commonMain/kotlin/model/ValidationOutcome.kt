package model

expect class ValidationOutcome() {
    fun getFileInfo(): FileInfo
    fun setFileInfo(fileInfo: FileInfo): ValidationOutcome
    fun getMessages(): List<ValidationMessage>
    fun setMessages(issues: List<ValidationMessage>): ValidationOutcome
}
