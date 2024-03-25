package controller.validation

import org.hl7.fhir.validation.ValidationEngine
import org.hl7.fhir.validation.cli.services.ValidationService

interface ValidationServiceFactory {

    fun  getValidationService() : ValidationService

    fun getCachedValidator(sessionId: String?) : ValidationEngine?
}