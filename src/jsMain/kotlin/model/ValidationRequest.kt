package model

import kotlinx.serialization.Serializable

@Serializable
actual class ValidationRequest actual constructor() {

    private var cliContext: CliContext = CliContext()
    private var filesToValidate: List<FileInfo> = listOf()

    constructor(cliContext: CliContext, filesToValidate: List<FileInfo>) : this() {
        this.cliContext = cliContext
        this.filesToValidate = filesToValidate
    }

    actual fun getCliContext(): CliContext {
        return cliContext
    }

    actual fun setCliContext(cliContext: CliContext): ValidationRequest {
        this.cliContext = cliContext
        return this
    }

    actual fun getFilesToValidate(): List<FileInfo> {
        return filesToValidate
    }

    actual fun setFilesToValidate(filesToValidate: List<FileInfo>): ValidationRequest {
        this.filesToValidate = filesToValidate
        return this
    }
}