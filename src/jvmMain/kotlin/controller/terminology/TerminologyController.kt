package controller.terminology

interface TerminologyController {
    suspend fun isTerminologyServerValid(capabilityStatement: String): Boolean
}