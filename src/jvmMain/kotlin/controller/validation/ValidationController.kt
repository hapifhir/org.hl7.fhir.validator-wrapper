package controller.validation

import model.AppVersion
import model.ValidationResponse
import org.hl7.fhir.validation.cli.model.ValidationRequest

interface ValidationController {
    suspend fun validateRequest(validationRequest: ValidationRequest): ValidationResponse

    suspend fun getAppVersion() : AppVersion
}