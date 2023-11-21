package model

import kotlinx.serialization.Serializable

@Serializable
actual class ValidationResponse actual constructor() {

    private var outcomes: List<ValidationOutcome> = listOf()
    private var sessionId: String = ""
    private var validationTimes: Map<String, ValidationTime> = emptyMap()

    constructor(outcomes: List<ValidationOutcome>) : this() {
        this.outcomes = outcomes
    }

    constructor(outcomes: List<ValidationOutcome>, sessionId: String, validationTimes: Map<String, ValidationTime>) :this() {
        this.outcomes = outcomes
        this.sessionId = sessionId
        this.validationTimes = validationTimes
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

    actual fun getValidationTimes(): Map<String, ValidationTime> {
        return validationTimes
    }

    actual fun setValidationTimes(validationTimes: Map<String, ValidationTime>): ValidationResponse {
       this.validationTimes = validationTimes
        return this
    }
}