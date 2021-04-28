package controller.terminology

import model.CapabilityStatement

interface TerminologyController {
    suspend fun isTerminologyServerValid(capabilityStatement: CapabilityStatement): Boolean
}