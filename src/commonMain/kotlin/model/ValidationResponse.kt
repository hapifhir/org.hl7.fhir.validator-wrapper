package model

expect class ValidationResponse() {
    fun getOutcomes(): List<ValidationOutcome>
    fun setOutcomes(outcomes: List<ValidationOutcome>): ValidationResponse
    fun getSessionId(): String
    fun setSessionId(sessionId: String): ValidationResponse

    fun getValidationTimes(): Map<String, ValidationTime>

    fun setValidationTimes(validationTimes: Map<String, ValidationTime>) : ValidationResponse
}