package controller.validation

import io.ktor.application.*
import org.hl7.fhir.validation.cli.services.ValidationService

private const val MIN_FREE_MEMORY = 2500000000

class ValidationServiceFactoryImpl : ValidationServiceFactory {

    private var validationService = ValidationService();

    override fun getValidationService() : ValidationService {
        if (java.lang.Runtime.getRuntime().freeMemory() < MIN_FREE_MEMORY) {
            println("Free memory ${java.lang.Runtime.getRuntime().freeMemory()} is less than ${MIN_FREE_MEMORY}. Re-initializing validationService");
            validationService = ValidationService();
        }
        return validationService;
    }
}