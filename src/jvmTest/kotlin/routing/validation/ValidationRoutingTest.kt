package routing.validation

import constants.VALIDATION_ENDPOINT
import constants.VALIDATOR_VERSION_ENDPOINT
import controller.validation.INVALID_FILE_MESSAGE
import controller.validation.NO_FILES_PROVIDED_MESSAGE
import controller.validation.ValidationController
import controller.validation.validationModule
import instrumentation.ValidationInstrumentation.compareValidationResponses
import instrumentation.ValidationInstrumentation.givenAValidationRequest
import instrumentation.ValidationInstrumentation.givenAValidationRequestWithABadFileName
import instrumentation.ValidationInstrumentation.givenAValidationRequestWithABadFileType
import instrumentation.ValidationInstrumentation.givenAValidationRequestWithNoFiles
import instrumentation.ValidationInstrumentation.givenAValidationRequestWithNullListOfFiles
import instrumentation.ValidationInstrumentation.givenAValidationResult
import instrumentation.ValidationInstrumentation.givenAnInternalValidatorError
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.routing.*
import io.mockk.coEvery
import io.mockk.mockk
import model.AppVersions
import org.hl7.fhir.validation.service.model.ValidationResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.koin.core.module.Module
import routing.BaseRoutingTest

class ValidationRoutingTest : BaseRoutingTest() {

    private val validationController: ValidationController = mockk()

    override fun Module.getKoinModules() {
        single<ValidationController> { validationController }
    }

    override fun Routing.getRoutingModules() {
        validationModule()
    }

    @Test
    fun `when requesting validation with a valid request, return validation response body`() = withTestApplication {
        val validationRequest = givenAValidationRequest()
        val validationResponse = givenAValidationResult(10, 10)
        coEvery { validationController.validateRequest(any()) } returns validationResponse

        val response = client.post(VALIDATION_ENDPOINT) {
            contentType(ContentType.Application.Json)
            setBody(toJsonBody(validationRequest))
        }

        assertEquals(HttpStatusCode.OK, response.status)
        val responseBody = response.parseBody(ValidationResponse::class.java)
        compareValidationResponses(validationResponse, responseBody)
    }

    @Test
    fun `test internal exception from validation service results in internal server error`() = withTestApplication {
        val validationRequest = givenAValidationRequest()
        val internalError = givenAnInternalValidatorError()
        coEvery { validationController.validateRequest(any()) } throws internalError

        val response = client.post(VALIDATION_ENDPOINT) {
            contentType(ContentType.Application.Json)
            setBody(toJsonBody(validationRequest))
        }

        assertEquals(HttpStatusCode.InternalServerError, response.status)
        assertEquals(internalError.localizedMessage, response.parseBody(String::class.java))
    }

    @Test
    fun `test sending a request containing no files results in bad request returned`() = withTestApplication {
        val validationRequest = givenAValidationRequestWithNoFiles()
        val internalError = givenAnInternalValidatorError()
        coEvery { validationController.validateRequest(any()) } throws internalError

        val response = client.post(VALIDATION_ENDPOINT) {
            contentType(ContentType.Application.Json)
            setBody(toJsonBody(validationRequest))
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
        assertEquals(NO_FILES_PROVIDED_MESSAGE, response.parseBody(String::class.java))
    }

    @Test
    fun `test sending a request containing null results in bad request returned`() = withTestApplication {
        val validationRequest = givenAValidationRequestWithNullListOfFiles()
        val internalError = givenAnInternalValidatorError()
        coEvery { validationController.validateRequest(any()) } throws internalError

        val response = client.post(VALIDATION_ENDPOINT) {
            contentType(ContentType.Application.Json)
            setBody(toJsonBody(validationRequest))
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
        assertEquals(NO_FILES_PROVIDED_MESSAGE, response.parseBody(String::class.java))
    }

    @Test
    fun `test sending a request containing a bad filetype results in bad request returned`() = withTestApplication {
        val validationRequest = givenAValidationRequestWithABadFileType()
        val internalError = givenAnInternalValidatorError()
        coEvery { validationController.validateRequest(any()) } throws internalError

        val response = client.post(VALIDATION_ENDPOINT) {
            contentType(ContentType.Application.Json)
            setBody(toJsonBody(validationRequest))
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
        assertEquals(INVALID_FILE_MESSAGE, response.parseBody(String::class.java))
    }
    @Test
    fun `test sending a request containing a bad filename results in bad request returned`() = withTestApplication {
        val validationRequest = givenAValidationRequestWithABadFileName()
        val internalError = givenAnInternalValidatorError()
        coEvery { validationController.validateRequest(any()) } throws internalError

        val response = client.post(VALIDATION_ENDPOINT) {
            contentType(ContentType.Application.Json)
            setBody(toJsonBody(validationRequest))
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
        assertEquals(INVALID_FILE_MESSAGE, response.parseBody(String::class.java))
    }

    @Test
    fun `test sending a request for validator version`() = withTestApplication {
        coEvery { validationController.getAppVersions() } returns AppVersions("dummy.version.1", "dummy.version.2")

        val response = client.get(VALIDATOR_VERSION_ENDPOINT) {
            contentType(ContentType.Application.Json)
        }

        assertEquals(HttpStatusCode.OK, response.status)
        val appVersions = response.parseBody(AppVersions::class.java)
        assertEquals("dummy.version.1", appVersions.validatorWrapperVersion)
        assertEquals("dummy.version.2", appVersions.validatorVersion)
    }
}