package model

import kotlinx.serialization.Serializable

@Serializable
actual class ValidationResponse actual constructor() {

    private var outcomes: List<ValidationOutcome> = listOf()
    private var sessionId: String = ""

    constructor(outcomes: List<ValidationOutcome>) : this() {
        this.outcomes = outcomes
    }

    constructor(outcomes: List<ValidationOutcome>, sessionId: String) : this() {
        this.outcomes = outcomes
        this.sessionId = sessionId
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
}