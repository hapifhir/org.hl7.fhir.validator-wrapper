package controller.validation

import model.Preset
import org.hl7.fhir.validation.cli.services.ValidationService

interface ValidationServiceFactory {

    fun getValidationPresets(): List<Preset>

    fun  getValidationService() : ValidationService
}