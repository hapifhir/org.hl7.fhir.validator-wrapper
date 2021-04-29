package api.terminogy

import model.CapabilityStatement

interface TerminologyApi {
    suspend fun getCapabilityStatement(serverUrl: String): CapabilityStatement
}