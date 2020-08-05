package model

expect class ValidationRequest() {
    fun getCliContext(): CliContext
    fun setCliContext(cliContext: CliContext): ValidationRequest
    fun getFilesToValidate(): List<FileInfo>
    fun setFilesToValidate(filesToValidate: List<FileInfo>): ValidationRequest
}