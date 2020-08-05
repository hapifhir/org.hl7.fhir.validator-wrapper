package model

expect class ValidationIssue() {
    fun getSeverity(): String
    fun setSeverity(severity: String): ValidationIssue
    fun getDetails(): String
    fun setDetails(details: String): ValidationIssue
}
