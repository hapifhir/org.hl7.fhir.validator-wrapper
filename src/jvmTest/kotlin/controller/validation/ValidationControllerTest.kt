package controller.validation

import controller.BaseControllerTest
import instrumentation.ValidationInstrumentation.compareValidationResponses
import instrumentation.ValidationInstrumentation.givenAValidationRequest
import instrumentation.ValidationInstrumentation.givenAValidationResult
import instrumentation.ValidationInstrumentation.givenAnInternalValidatorError
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.hl7.fhir.validation.cli.services.ValidationService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.koin.dsl.module
import java.io.File
import java.io.FileInputStream
import java.util.*
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ValidationControllerTest : BaseControllerTest() {
    private val validationService: ValidationService = mockk()
    private val validationServiceFactory : ValidationServiceFactory = mockk()
    private val validationController: ValidationController by lazy { ValidationControllerImpl() }

    init {
        startInjection(
            module {
                single() { validationServiceFactory }
            }
        )
    }

    @BeforeEach
    override fun before() {
        super.before()
        clearMocks(validationServiceFactory)
        clearMocks(validationService)
        every {validationServiceFactory.getValidationService() } returns validationService;
    }

    @Test
    fun `test happy path, single validation outcome from ValidationService`() {
        val validationResult = givenAValidationResult(numOutcomes = 1, numMessages = 1)
        coEvery { validationServiceFactory.getValidationService().validateSources(any()) } returns validationResult

        runBlocking {
            val response = validationController.validateRequest(givenAValidationRequest())
            compareValidationResponses(expected = validationResult, actual = response)
        }
    }

    @Test
    fun `test happy path, multi validation outcome from ValidationService`() {
        val validationResult = givenAValidationResult(numOutcomes = 20, numMessages = 4)
        coEvery { validationServiceFactory.getValidationService().validateSources(any()) } returns validationResult

        runBlocking {
            val response = validationController.validateRequest(givenAValidationRequest())
            compareValidationResponses(expected = validationResult, actual = response)
        }
    }

    @Test
    fun `test internal exception from ValidationService`() {
        val internalError = givenAnInternalValidatorError()
        coEvery { validationServiceFactory.getValidationService().validateSources(any()) } throws internalError
        val exception = Assertions.assertThrows(Exception::class.java) {
            runBlocking { validationController.validateRequest(givenAValidationRequest()) }
        }
        Assertions.assertEquals(internalError.localizedMessage, exception.localizedMessage)
    }

    @Test
    fun `test happy path, validator version ValidationService`() {
        val prop = Properties().apply {
            load(FileInputStream(File("gradle.properties")))
            load(FileInputStream(File("version.properties")))
        }

        val fhirCoreVersion = prop.get("fhirCoreVersion")
        val wrapperVersion = prop.get("version.semver")

        runBlocking {
            val response = validationController.getAppVersion()
            assertEquals(expected = fhirCoreVersion, actual = response.coreVersion)
            assertEquals(expected = wrapperVersion, actual = response.wrapperVersion)

        }
    }
}