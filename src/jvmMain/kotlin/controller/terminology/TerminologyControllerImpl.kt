package controller.terminology

import model.CapabilityStatement
import org.koin.core.KoinComponent

class TerminologyControllerImpl : TerminologyController, KoinComponent {

    val TERMINOLOGY_CAP_STATEMENT = "http://hl7.org/fhir/CapabilityStatement/terminology-server"

    override suspend fun isTerminologyServerValid(capabilityStatement: CapabilityStatement): Boolean {
        capabilityStatement.instantiates?.forEach { canonicalType ->
            if (canonicalType != null) {
                println("Cap statement -> ${canonicalType.valueAsString}")
                if (canonicalType.valueAsString == TERMINOLOGY_CAP_STATEMENT) return true
            }
        }
        return false
    }
}