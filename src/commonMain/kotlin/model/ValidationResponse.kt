package model

expect class ValidationResponse() {
    fun getOutcomes(): List<ValidationOutcome>
    fun setOutcomes(outcomes: List<ValidationOutcome>): ValidationResponse
}