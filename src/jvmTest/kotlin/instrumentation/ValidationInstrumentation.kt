package instrumentation

import constants.FhirFormat
import model.*
import org.hl7.fhir.validation.service.model.ValidationRequest
import kotlin.test.assertEquals

object ValidationInstrumentation {

    private val validationResults = listOf(
        Pair("Picard was the best starship captain.", IssueSeverity.INFORMATION),
        Pair("A hot dog is not a sandwich.", IssueSeverity.ERROR),
        Pair("It's pronounced 'G'if, not 'J'if. I don't care what Steve Wilhite says.", IssueSeverity.WARNING),
        Pair("You pour cereal into the bowl first, then milk. Never the opposite.", IssueSeverity.FATAL),
    )

    fun givenAnInternalValidatorError(): Exception {
        return Exception("Danger Will Robinson!")
    }

    fun givenAValidationRequest(): ValidationRequest {
        val listOfFiles = mutableListOf(FileInfo().setFileName("DUMMY_NAME")
            .setFileType(FhirFormat.JSON.code)
            .setFileContent("DUMMY_CONTENT"))
        return ValidationRequest().setValidationContext(ValidationContext()).setFilesToValidate(listOfFiles).setSessionId("DUMMY_SESSION_ID")
    }

    fun givenAValidationRequestWithNoFiles(): ValidationRequest {
        val listOfFiles = mutableListOf<FileInfo>()
        return ValidationRequest().setValidationContext(ValidationContext()).setFilesToValidate(listOfFiles).setSessionId("DUMMY_SESSION_ID")
    }

    fun givenAValidationRequestWithNullListOfFiles(): ValidationRequest {
        return ValidationRequest().setValidationContext(ValidationContext()).setFilesToValidate(null).setSessionId("DUMMY_SESSION_ID")
    }

    fun givenAValidationRequestWithABadFileType(): ValidationRequest {
        val listOfFiles = mutableListOf(
            FileInfo().setFileName("DUMMY_NAME").setFileType(FhirFormat.JSON.code).setFileContent("DUMMY_CONTENT"),
            FileInfo().setFileName("DUMMY_NAME").setFileType(".xcode").setFileContent("DUMMY_CONTENT")
        )
        return ValidationRequest().setValidationContext(ValidationContext()).setFilesToValidate(listOfFiles).setSessionId("DUMMY_SESSION_ID")
    }

    fun givenAValidationRequestWithABadFileName(): ValidationRequest {
        val listOfFiles = mutableListOf(
            FileInfo().setFileName("DUMMY_NAME").setFileType(FhirFormat.JSON.code).setFileContent("DUMMY_CONTENT"),
            FileInfo().setFileName("").setFileType(FhirFormat.XML.code).setFileContent("DUMMY_CONTENT")
        )
        return ValidationRequest().setValidationContext(ValidationContext()).setFilesToValidate(listOfFiles).setSessionId("DUMMY_SESSION_ID")
    }

    fun givenAValidationResult(numOutcomes: Int, numMessages: Int): ValidationResponse {
        val fhirValidationResult = ValidationResponse()
        for (i in 0 until numOutcomes - 1) {
            val validationOutcome = ValidationOutcome()
            for (j in 0 until numMessages - 1) {
                val validationMessage = ValidationMessage()
                validationMessage.message = validationResults[j % 4].first
                validationMessage.level = validationResults[j % 4].second
                validationOutcome.addMessage(validationMessage)
            }
            fhirValidationResult.addOutcome(validationOutcome)
        }
        return fhirValidationResult
    }

    fun compareValidationResponses(expected: ValidationResponse, actual: ValidationResponse) {
        assertEquals(expected.outcomes.size,
            actual.outcomes.size,
            "Expected ValidationResponse with ${actual.outcomes.size} outcome(s), counted ${actual.outcomes.size} instead.}")
        expected.outcomes.forEachIndexed { outcome_index, outcome ->
            assertEquals(outcome.messages.size,
                actual.outcomes[outcome_index].messages.size,
                "Expected outcome with ${outcome.messages.size} message, got ${actual.outcomes.first().messages.size} instead.}")
            outcome.messages.forEachIndexed { message_index, message ->
                assertEquals(message.level,
                    actual.outcomes[outcome_index].messages[message_index].level,
                    "Expected message error level ${message.level}, got ${actual.outcomes[outcome_index].messages[message_index].level} instead.}")
                assertEquals(message.message,
                    actual.outcomes[outcome_index].messages[message_index].message,
                    "Expected message ${message.message}, got ${actual.outcomes[outcome_index].messages[message_index].message} instead.}")
            }
        }
    }


}