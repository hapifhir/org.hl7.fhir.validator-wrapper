package model

import kotlinx.serialization.Serializable

@Serializable
actual class ValidationResponse actual constructor() {

    private var outcomes: List<ValidationOutcome> = listOf()
    private var sessionId: String = ""
    private var validationTime: ValidationTime? = ValidationTime()

    constructor(outcomes: List<ValidationOutcome>) : this() {
        this.outcomes = outcomes
    }

    constructor(outcomes: List<ValidationOutcome>, sessionId: String, validationTime: ValidationTime) :this() {
        this.outcomes = outcomes
        this.sessionId = sessionId
        this.validationTime = validationTime
    }

    actual fun getOutcomes(): List<ValidationOutcome> {
        return outcomes
    }

    actual fun setOutcomes(outcomes: List<ValidationOutcome>): ValidationResponse {
        this.outcomes = outcomes
        return this
    }

    actual fun getSessionId(): String {
        return sessionId
    }

    actual fun setSessionId(sessionId: String): ValidationResponse {
        this.sessionId = sessionId
        return this
    }

    actual fun getValidationTime(): ValidationTime? {
        return  validationTime
    }

    actual fun setValidationTime(validationTime: ValidationTime?): ValidationResponse {
       this.validationTime = validationTime
        return this
    }
}