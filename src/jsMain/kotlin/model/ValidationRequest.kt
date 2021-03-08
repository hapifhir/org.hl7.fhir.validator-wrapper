package model

import kotlinx.serialization.Serializable

@Serializable
actual class ValidationRequest actual constructor() {

    private var cliContext: CliContext = CliContext()
    private var filesToValidate: List<FileInfo> = listOf()
    private var sessionId: String = ""

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

    actual fun getSessionId(): String {
        return sessionId
    }

    actual fun setSessionId(sessionId: String): ValidationRequest {
        this.sessionId = sessionId
        return this
    }

}