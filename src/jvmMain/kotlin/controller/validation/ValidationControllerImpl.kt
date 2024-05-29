package controller.validation

import model.ValidationResponse
import org.hl7.fhir.utilities.VersionUtil
import org.hl7.fhir.validation.cli.model.ValidationRequest
import org.hl7.fhir.validation.cli.services.ValidationService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File
import java.io.FileInputStream
import java.util.*

class ValidationControllerImpl : ValidationController, KoinComponent {

    private val validationServiceFactory by inject<ValidationServiceFactory>()

    override suspend fun validateRequest(validationRequest: ValidationRequest): ValidationResponse {
        return validationServiceFactory.getValidationService().validateSources(validationRequest)
    }

    override suspend fun getValidatorVersion():String {
        return VersionUtil.getVersion()
    }

    override suspend fun getValidationEngines(): Set<String> {
        return validationServiceFactory.getValidationService().baseEngineKeys;
    }
}