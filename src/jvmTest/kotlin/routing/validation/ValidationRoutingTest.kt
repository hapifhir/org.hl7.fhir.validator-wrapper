package routing.validation

import constants.VALIDATION_ENDPOINT
import controller.validation.INVALID_FILE_MESSAGE
import controller.validation.NO_FILES_PROVIDED_MESSAGE
import controller.validation.ValidationController
import controller.validation.validationModule
import instrumentation.ValidationInstrumentation
import instrumentation.ValidationInstrumentation.compareValidationResponses
import instrumentation.ValidationInstrumentation.givenAValidationRequest
import instrumentation.ValidationInstrumentation.givenAValidationRequestWithABadFileName
import instrumentation.ValidationInstrumentation.givenAValidationRequestWithABadFileType
import instrumentation.ValidationInstrumentation.givenAValidationRequestWithNoFiles
import instrumentation.ValidationInstrumentation.givenAValidationRequestWithNullListOfFiles
import instrumentation.ValidationInstrumentation.givenAValidationResult
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.ContentType
import io.ktor.routing.*
import io.ktor.server.testing.*
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import model.ValidationResponse
import org.junit.jupiter.api.*
import org.koin.dsl.module
import routing.BaseRoutingTest
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ValidationRoutingTest : BaseRoutingTest() {

    private val validationController: ValidationController = mockk()

    @BeforeAll
    fun setup() {
        koinModules = module {
            single { validationController }
        }

        moduleList = {
            install(Routing) {
                validationModule()
            }
        }
    }

    @BeforeEach
    fun clearMocks() {
        io.mockk.clearMocks(validationController)
    }

    @Test
    fun `when requesting validation with a valid request, return validation response body`() = withBaseTestApplication {
        val validationRequest = givenAValidationRequest()
        val validationResponse = givenAValidationResult(10, 10)
        coEvery { validationController.validateRequest(any()) } returns validationResponse

        val call = handleRequest(HttpMethod.Post, VALIDATION_ENDPOINT) {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(toJsonBody(validationRequest))
        }

        with(call) {
            assertEquals(HttpStatusCode.OK, response.status())
            val responseBody = response.parseBody(ValidationResponse::class.java)
            compareValidationResponses(validationResponse, responseBody)
        }
    }

    @Test
    fun `test internal exception from validation service results in internal server error`() = withBaseTestApplication {
        val validationRequest = givenAValidationRequest()
        val internalError = ValidationInstrumentation.givenAnInternalValidatorError()
        coEvery { validationController.validateRequest(any()) } throws internalError

        val call = handleRequest(HttpMethod.Post, VALIDATION_ENDPOINT) {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(toJsonBody(validationRequest))
        }

        with(call) {
            assertEquals(HttpStatusCode.InternalServerError, response.status())
            assertEquals(internalError.localizedMessage, response.content)
        }
    }

    @Test
    fun `test sending a request containing no files results in bad request returned`() = withBaseTestApplication {
        val validationRequest = givenAValidationRequestWithNoFiles()
        val internalError = ValidationInstrumentation.givenAnInternalValidatorError()
        coEvery { validationController.validateRequest(any()) } throws internalError

        val call = handleRequest(HttpMethod.Post, VALIDATION_ENDPOINT) {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(toJsonBody(validationRequest))
        }

        with(call) {
            assertEquals(HttpStatusCode.BadRequest, response.status())
            assertEquals(NO_FILES_PROVIDED_MESSAGE, response.content)
        }
    }

    @Test
    fun `test sending a request containing null results in bad request returned`() = withBaseTestApplication {
        val validationRequest = givenAValidationRequestWithNullListOfFiles()
        val internalError = ValidationInstrumentation.givenAnInternalValidatorError()
        coEvery { validationController.validateRequest(any()) } throws internalError

        val call = handleRequest(HttpMethod.Post, VALIDATION_ENDPOINT) {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(toJsonBody(validationRequest))
        }

        with(call) {
            assertEquals(HttpStatusCode.BadRequest, response.status())
            assertEquals(NO_FILES_PROVIDED_MESSAGE, response.content)
        }
    }

    @Test
    fun `test sending a request containing a bad filetype results in bad request returned`() = withBaseTestApplication {
        val validationRequest = givenAValidationRequestWithABadFileType()
        val internalError = ValidationInstrumentation.givenAnInternalValidatorError()
        coEvery { validationController.validateRequest(any()) } throws internalError

        val call = handleRequest(HttpMethod.Post, VALIDATION_ENDPOINT) {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(toJsonBody(validationRequest))
        }

        with(call) {
            assertEquals(HttpStatusCode.BadRequest, response.status())
            assertEquals(INVALID_FILE_MESSAGE, response.content)
        }
    }

    @Test
    fun `test sending a request containing a bad filename results in bad request returned`() = withBaseTestApplication {
        val validationRequest = givenAValidationRequestWithABadFileName()
        val internalError = ValidationInstrumentation.givenAnInternalValidatorError()
        coEvery { validationController.validateRequest(any()) } throws internalError

        val call = handleRequest(HttpMethod.Post, VALIDATION_ENDPOINT) {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(toJsonBody(validationRequest))
        }

        with(call) {
            assertEquals(HttpStatusCode.BadRequest, response.status())
            assertEquals(INVALID_FILE_MESSAGE, response.content)
        }
    }
}