package routes

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import constants.FhirFormat
import constants.VALIDATION_ENDPOINT
import io.ktor.http.*
import io.ktor.server.testing.*
import io.mockk.every
import io.mockk.mockkStatic
import model.*
import org.hl7.fhir.exceptions.FHIRException
import org.hl7.fhir.validation.cli.services.ValidationService
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class ValidationRouteTests {

    private lateinit var objMapper: ObjectMapper

    private val validationResults = listOf(
        Pair("Picard was the best starship captain.", IssueSeverity.INFORMATION),
        Pair("A hot dog is not a sandwich.", IssueSeverity.ERROR),
        Pair("It's pronounced 'G'if, not 'J'if. I don't care what Steve Wilhite says.", IssueSeverity.WARNING),
        Pair("You pour cereal into the bowl first, then milk. Never the opposite.", IssueSeverity.FATAL),
    )

    @Before
    fun setup() {
        objMapper = ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    @Test
    fun `Happy path, single validation outcome from ValidationService`() = testWithApp() {
        val fhirValidationResult = createValidationResult(1, 1)
        mockkStatic(ValidationService::class)
        every { ValidationService.validateSources(any()) } returns fhirValidationResult

        val request = generateValidDummyValidationRequest()

        handleRequest(HttpMethod.Post, VALIDATION_ENDPOINT) {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(objMapper.writeValueAsString(request))
        }.apply {
            assertEquals(HttpStatusCode.OK, response.status())
            val validationResponse = response.content?.let {
                objMapper.readValue<ValidationResponse>(it)
            } ?: fail("Null ValidationResponse.")
            compareValidationResponses(fhirValidationResult, validationResponse)
        }
    }

    @Test
    fun `Happy path, multi-validation outcome from ValidationService`() = testWithApp() {
        val fhirValidationResult = createValidationResult(20, 4)
        mockkStatic(ValidationService::class)
        every { ValidationService.validateSources(any()) } returns fhirValidationResult

        val request = generateValidDummyValidationRequest()

        handleRequest(HttpMethod.Post, VALIDATION_ENDPOINT) {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(objMapper.writeValueAsString(request))
        }.apply {
            assertEquals(HttpStatusCode.OK, response.status())
            val validationResponse = response.content?.let {
                objMapper.readValue<ValidationResponse>(it)
            } ?: fail("Null ValidationResponse.")
            compareValidationResponses(fhirValidationResult, validationResponse)
        }
    }

    @Test
    fun `Internal exception thrown by FHIR validator`() = testWithApp() {
        val expectedErrorMessage = "Danger Will Robinson!"
        mockkStatic(ValidationService::class)
        every { ValidationService.validateSources(any()) } throws FHIRException(expectedErrorMessage)

        val request = generateValidDummyValidationRequest()

        handleRequest(HttpMethod.Post, VALIDATION_ENDPOINT) {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(objMapper.writeValueAsString(request))
        }.apply {
            assertEquals(HttpStatusCode.InternalServerError, response.status())
            assertEquals(expectedErrorMessage, response.content)
        }
    }

    @Test
    fun `No files provided in request for validation`() = testWithApp() {
        val request = ValidationRequest().setCliContext(CliContext())

        handleRequest(HttpMethod.Post, VALIDATION_ENDPOINT) {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(objMapper.writeValueAsString(request))
        }.apply {
            assertEquals(HttpStatusCode.BadRequest, response.status())
            assertEquals(NO_FILES_PROVIDED_MESSAGE, response.content)
        }
    }

    @Test
    fun `Bad file name provided`() = testWithApp() {
        val request = generateValidDummyValidationRequest()
        request.filesToValidate.first().fileName = ""

        handleRequest(HttpMethod.Post, VALIDATION_ENDPOINT) {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(objMapper.writeValueAsString(request))
        }.apply {
            assertEquals(HttpStatusCode.BadRequest, response.status())
            assertEquals(INVALID_FILE_MESSAGE, response.content)
        }

        request.filesToValidate.first().fileName = null

        handleRequest(HttpMethod.Post, VALIDATION_ENDPOINT) {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(objMapper.writeValueAsString(request))
        }.apply {
            assertEquals(HttpStatusCode.BadRequest, response.status())
            assertEquals(INVALID_FILE_MESSAGE, response.content)
        }
    }

    @Test
    fun `Bad file type provided`() = testWithApp() {
        val request = generateValidDummyValidationRequest()
        request.filesToValidate.first().fileType = "pdf"

        handleRequest(HttpMethod.Post, VALIDATION_ENDPOINT) {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(objMapper.writeValueAsString(request))
        }.apply {
            assertEquals(HttpStatusCode.BadRequest, response.status())
            assertEquals(INVALID_FILE_MESSAGE, response.content)
        }

        request.filesToValidate.first().fileType = null

        handleRequest(HttpMethod.Post, VALIDATION_ENDPOINT) {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(objMapper.writeValueAsString(request))
        }.apply {
            assertEquals(HttpStatusCode.BadRequest, response.status())
            assertEquals(INVALID_FILE_MESSAGE, response.content)
        }
    }

    @Test
    fun `Null file content provided`() = testWithApp() {
        val request = generateValidDummyValidationRequest()
        request.filesToValidate.first().fileContent = null

        handleRequest(HttpMethod.Post, VALIDATION_ENDPOINT) {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(objMapper.writeValueAsString(request))
        }.apply {
            assertEquals(HttpStatusCode.BadRequest, response.status())
            assertEquals(INVALID_FILE_MESSAGE, response.content)
        }
    }

    private fun generateValidDummyValidationRequest(): org.hl7.fhir.validation.cli.model.ValidationRequest {
        // Add a dummy file with no data so as to not trigger bad request return
        val listOfFiles = mutableListOf(FileInfo().setFileName("DUMMY_NAME").setFileType(FhirFormat.JSON.code)
            .setFileContent("DUMMY_CONTENT"))
        return ValidationRequest().setCliContext(CliContext()).setFilesToValidate(listOfFiles)
    }

    private fun createValidationResult(numOutcomes: Int, numMessages: Int): ValidationResponse {
        val fhirValidationResult = ValidationResponse()
        for (i in 0 until numOutcomes - 1) {
            val validationOutcome = ValidationOutcome()
            for (j in 0 until numMessages - 1) {
                val validationMessage = ValidationMessage()
                validationMessage.message = validationResults[j].first
                validationMessage.level = validationResults[j].second
                validationOutcome.addMessage(validationMessage)
            }
            fhirValidationResult.addOutcome(validationOutcome)
        }
        return fhirValidationResult
    }

    private fun compareValidationResponses(expected: ValidationResponse, actual: ValidationResponse) {
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