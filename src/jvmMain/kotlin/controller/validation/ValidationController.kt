package controller.validation

import model.AppVersions
import model.ValidationResponse
import org.hl7.fhir.validation.cli.model.ValidationRequest

interface ValidationController {
    suspend fun validateRequest(validationRequest: ValidationRequest): ValidationResponse

    suspend fun getValidationEngines() : Set<String>

    suspend fun getAppVersions() : AppVersions
}