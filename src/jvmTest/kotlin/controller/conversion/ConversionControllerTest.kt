package controller.conversion

import controller.BaseControllerTest
import controller.validation.ValidationServiceFactory
import instrumentation.ValidationInstrumentation.compareValidationResponses
import instrumentation.ValidationInstrumentation.givenAValidationRequest
import instrumentation.ValidationInstrumentation.givenAValidationResult
import instrumentation.ValidationInstrumentation.givenAnInternalValidatorError
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.hl7.fhir.validation.ValidationEngine
import org.hl7.fhir.validation.cli.model.CliContext
import org.hl7.fhir.validation.cli.services.ValidationService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.koin.dsl.module
import java.io.File
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import kotlin.io.path.Path
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConversionControllerTest : BaseControllerTest() {
    private val validationService: ValidationService = mockk()
    private val validationEngine: ValidationEngine = mockk()
    private val validationServiceFactory : ValidationServiceFactory = mockk()
    private val conversionController: ConversionController by lazy { ConversionControllerImpl() }

    private val conversionXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<Patient xmlns=\"http://hl7.org/fhir\">\n" +
            "    <id value=\"1\"/>\n" +
            "</Patient>"

    private val conversionJson = "{\n" +
            "    \"resourceType\": \"Patient\",\n" +
            "    \"id\": \"1\"\n" +
            "}"

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
        clearMocks(validationEngine)
        every {validationServiceFactory.getValidationService() } returns validationService;
        every {validationServiceFactory.getValidationEngine(any())} returns validationEngine;
    }

    @Test
    fun `test happy path`() {
        val cliContextSlot = slot<CliContext>()
        coEvery {
            validationServiceFactory.getValidationService().convertSources(capture(cliContextSlot), any())
        } answers {
            val cliContext = cliContextSlot.captured
            assertTrue(cliContext.sources[0].endsWith(".json"))
            assertEquals("5.0", cliContext.sv)
            assertTrue(cliContext.output.endsWith(".xml"))
            assertEquals("4.0", cliContext.targetVer)
            val content = Files.readString(Path(cliContextSlot.captured.sources[0]))
            assertEquals(conversionJson, content)
        }

        runBlocking {
            val result = conversionController.convertRequest(conversionJson, "json", "5.0", "xml",
                "4.0")
            assertEquals("", result)
        }
    }

    @Test
    fun `test internal exception from ValidationService`() {
        val internalError = Exception("Convert sources failed!")
        coEvery { validationServiceFactory.getValidationService().convertSources(any(), any()) } throws internalError
        val exception = Assertions.assertThrows(Exception::class.java) {
            runBlocking { conversionController.convertRequest(conversionJson) }
        }
        Assertions.assertEquals(internalError.localizedMessage, exception.localizedMessage)
    }

}