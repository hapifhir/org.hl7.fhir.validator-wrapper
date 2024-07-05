package controller.validation

import model.ValidationResponse
import org.hl7.fhir.utilities.VersionUtil
import org.hl7.fhir.validation.cli.model.ValidationRequest
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.IOException
import java.util.*

class ValidationControllerImpl : ValidationController, KoinComponent {

    private val validationServiceFactory by inject<ValidationServiceFactory>()

    override suspend fun validateRequest(validationRequest: ValidationRequest): ValidationResponse {
        return validationServiceFactory.getValidationService().validateSources(validationRequest)
    }

    override suspend fun getValidatorVersion():String {
        println(getWrapperVersion());
        return VersionUtil.getVersion()
    }

    fun getWrapperVersion(): String {
        val properties = Properties()
        try {
            Thread.currentThread().contextClassLoader.getResourceAsStream("version.properties").use { inputStream ->
                if (inputStream == null) {
                    return "(file not found)"
                }
                properties.load(inputStream)
                return properties.getProperty("version.semver")
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return "(error reading version)"
        }
    }
}