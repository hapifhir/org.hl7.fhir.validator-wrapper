package controller.validation

import model.ValidationResponse
import org.hl7.fhir.validation.cli.model.ValidationRequest

interface ValidationController {

    fun initSession(ig: String): String?

    suspend fun validateRequest(validationRequest: ValidationRequest): ValidationResponse

    suspend fun getValidatorVersion() : String

    suspend fun getSessionId(ig: String): String?

    suspend fun getDefaultSessionId(): String? 
}