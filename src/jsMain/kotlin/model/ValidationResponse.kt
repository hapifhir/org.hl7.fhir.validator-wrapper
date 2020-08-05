package model

import kotlinx.serialization.Serializable

@Serializable()
actual class ValidationResponse actual constructor() {

    private var outcomes: List<ValidationOutcome> = listOf()

    constructor(outcomes: List<ValidationOutcome>) : this() {
        this.outcomes = outcomes
    }

    actual fun getOutcomes(): List<ValidationOutcome> {
        return outcomes
    }

    actual fun setOutcomes(outcomes: List<ValidationOutcome>): ValidationResponse {
        this.outcomes = outcomes
        return this
    }
}