package controller.validation

import model.AppVersions
import model.ValidationResponse
import org.hl7.fhir.utilities.VersionUtil
import org.hl7.fhir.validation.cli.model.ValidationRequest
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

class ValidationControllerImpl : ValidationController, KoinComponent {

    private val validationServiceFactory by inject<ValidationServiceFactory>()

    override suspend fun validateRequest(validationRequest: ValidationRequest): ValidationResponse {
        return validationServiceFactory.getValidationService().validateSources(validationRequest)
    }

    private fun getValidatorWrapperVersion() : String {
        val properties = Properties()
        val inputStream = Thread.currentThread().contextClassLoader.getResourceAsStream("version.properties")
        inputStream?.use {
            properties.load(it)
        }
        return properties.getProperty("version.semver")

    }

    override suspend fun getAppVersion(): AppVersions {
        return AppVersions(getValidatorWrapperVersion(), VersionUtil.getVersion())
    }
}