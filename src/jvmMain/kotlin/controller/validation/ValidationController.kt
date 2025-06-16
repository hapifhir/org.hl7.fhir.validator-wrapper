package controller.validation

import model.AppVersions
import model.Preset
import model.ValidationResponse
import org.hl7.fhir.validation.service.model.ValidationRequest

interface ValidationController {
    suspend fun validateRequest(validationRequest: ValidationRequest): ValidationResponse

    suspend fun getAppVersions() : AppVersions

    suspend fun getValidationPresets(): List<Preset>
}