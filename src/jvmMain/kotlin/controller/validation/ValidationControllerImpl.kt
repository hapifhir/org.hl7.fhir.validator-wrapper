package controller.validation

import model.ValidationResponse
import org.hl7.fhir.validation.cli.utils.VersionUtil
import org.hl7.fhir.validation.cli.model.ValidationRequest
import org.hl7.fhir.validation.cli.services.ValidationService
import org.koin.core.KoinComponent
import org.koin.core.inject

class ValidationControllerImpl : ValidationController, KoinComponent {

    private val validationServiceFactory by inject<ValidationServiceFactory>()

    override suspend fun validateRequest(validationRequest: ValidationRequest): ValidationResponse {
        return validationServiceFactory.getValidationService().validateSources(validationRequest)
    }

    override suspend fun getValidatorVersion():String {
        return VersionUtil.getVersion()
    }
}