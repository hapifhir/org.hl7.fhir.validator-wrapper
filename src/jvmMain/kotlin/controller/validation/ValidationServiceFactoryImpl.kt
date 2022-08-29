package controller.validation

import org.hl7.fhir.validation.cli.services.ValidationService

class ValidationServiceFactoryImpl : ValidationServiceFactory {

    private var validationService = ValidationService();

    override fun getValidationService() : ValidationService {
        if (java.lang.Runtime.getRuntime().freeMemory() < 2500000000) {
            validationService = ValidationService();
        }
        return validationService;
    }
}