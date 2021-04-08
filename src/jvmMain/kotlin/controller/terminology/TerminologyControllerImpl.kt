package controller.terminology

import org.koin.core.KoinComponent

class TerminologyControllerImpl : TerminologyController, KoinComponent {

    private val XML_CAP_STMT_TX = "<instantiates value=\"http://hl7.org/fhir/CapabilityStatement/terminology-server\"/>"
    private val JSON_CAP_STMT_TX = "\"instantiates\":\"http://hl7.org/fhir/CapabilityStatement/terminology-server\""
    private val JSON_CAP_STMT_TX_2 = "\"instantiates\":[\"http://hl7.org/fhir/CapabilityStatement/terminology-server\"]"

    override suspend fun isTerminologyServerValid(capabilityStatement: String): Boolean {
        return (capabilityStatement.contains(XML_CAP_STMT_TX)
                || capabilityStatement.contains(JSON_CAP_STMT_TX)
                || capabilityStatement.contains(JSON_CAP_STMT_TX_2))
    }
}