package model

import kotlinx.serialization.Serializable

@Serializable
actual class ValidationIssue actual constructor() {

    private var severity: String = ""
    private var details: String = ""

    constructor(severity: String, details: String) : this() {
        this.severity = severity
        this.details = details
    }

    actual fun getSeverity(): String {
        return severity
    }

    actual fun setSeverity(severity: String): ValidationIssue {
        this.severity = severity
        return this
    }

    actual fun getDetails(): String {
        return details
    }

    actual fun setDetails(details: String): ValidationIssue {
        this.details = details
        return this
    }
}