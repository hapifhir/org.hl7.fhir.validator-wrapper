package routing.conversion

import constants.CONVERSION_ENDPOINT
import controller.conversion.ConversionController
import controller.conversion.conversionModule

import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.http.ContentType
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.jupiter.api.*
import org.koin.dsl.module
import routing.BaseRoutingTest
import java.nio.charset.Charset
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConversionRoutingTest : BaseRoutingTest() {

    private val conversionController: ConversionController = mockk()

    private val conversionXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<Patient xmlns=\"http://hl7.org/fhir\">\n" +
            "    <id value=\"1\"/>\n" +
            "</Patient>"

    private val conversionJson = "{\n" +
            "    \"resourceType\": \"Patient\",\n" +
            "    \"id\": \"1\"\n" +
            "}"

    @BeforeAll
    fun setup() {
        koinModules = module {
            single { conversionController }
        }

        moduleList = {
            install(Routing) {
                conversionModule()
            }
        }
    }

    @BeforeEach
    fun clearMocks() {
        io.mockk.clearMocks(conversionController)
    }

    @Test
    fun `when requesting conversion with a valid request, return conversion response body`() = withBaseTestApplication {
        coEvery { conversionController.convertRequest(conversionXml, "xml", "4.0", "json", "4.0") } returns conversionJson

        val call = handleRequest(HttpMethod.Post, CONVERSION_ENDPOINT) {
            addHeader(HttpHeaders.ContentType, "application/fhir+xml; fhirVersion=4.0")
            addHeader(HttpHeaders.Accept, "application/fhir+json; fhirVersion=4.0")
            setBody(conversionXml)
        }

        with(call) {
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals(ContentType("application", "fhir+json")
                .withParameter("fhirVersion", "4.0").withCharset(Charset.defaultCharset()),
                response.contentType().withCharset(Charset.defaultCharset()))
            assertEquals(conversionJson, response.content)
        }
    }

    @Test
    fun `when requesting conversion with params override headers`() = withBaseTestApplication {
        coEvery { conversionController.convertRequest(conversionJson, "json", "5.0", "xml", "3.0") } returns conversionXml

        val call = handleRequest(HttpMethod.Post, CONVERSION_ENDPOINT + "?type=json&version=5.0&toType=xml&toVersion=3.0") {
            addHeader(HttpHeaders.ContentType, "application/fhir+xml; fhirVersion=4.0")
            addHeader(HttpHeaders.Accept, "application/fhir+json; fhirVersion=4.0")
            setBody(conversionJson)
        }

        with(call) {
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals(ContentType("application", "fhir+xml")
                .withParameter("fhirVersion", "3.0").withCharset(Charset.defaultCharset()),
                response.contentType().withCharset(Charset.defaultCharset()))
            assertEquals(conversionXml, response.content)
        }
    }

    @Test
    fun `when requesting conversion with toType and toVersion params combine with header`() = withBaseTestApplication {
        coEvery { conversionController.convertRequest(conversionJson, "json", "4.0", "xml", "3.0") } returns conversionXml

        val call = handleRequest(HttpMethod.Post, CONVERSION_ENDPOINT + "?toType=xml&toVersion=3.0") {
            addHeader(HttpHeaders.ContentType, "application/fhir+json; fhirVersion=4.0")
            setBody(conversionJson)
        }

        with(call) {
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals(ContentType("application", "fhir+xml")
                .withParameter("fhirVersion", "3.0").withCharset(Charset.defaultCharset()),
                response.contentType().withCharset(Charset.defaultCharset()))
            assertEquals(conversionXml, response.content)
        }
    }

    @Test
    fun `when requesting conversion with toType param combine with header`() = withBaseTestApplication {
        coEvery { conversionController.convertRequest(conversionJson, "json", "4.0", "xml", "4.0") } returns conversionXml

        val call = handleRequest(HttpMethod.Post, CONVERSION_ENDPOINT + "?toType=xml") {
            addHeader(HttpHeaders.ContentType, "application/fhir+json; fhirVersion=4.0")
            setBody(conversionJson)
        }

        with(call) {
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals(ContentType("application", "fhir+xml")
                .withParameter("fhirVersion", "4.0").withCharset(Charset.defaultCharset()),
                response.contentType().withCharset(Charset.defaultCharset()))
            assertEquals(conversionXml, response.content)
        }
    }

    @Test
    fun `when requesting conversion with toType and without custom headers use defaults`() = withBaseTestApplication {
        coEvery { conversionController.convertRequest(conversionJson, "json", "5.0", "xml", "5.0") } returns conversionXml

        val call = handleRequest(HttpMethod.Post, CONVERSION_ENDPOINT + "?toType=xml") {
            addHeader(HttpHeaders.ContentType, "application/json")
            setBody(conversionJson)
        }

        with(call) {
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals(ContentType("application", "fhir+xml")
                .withParameter("fhirVersion", "5.0").withCharset(Charset.defaultCharset()),
                response.contentType().withCharset(Charset.defaultCharset()))
            assertEquals(conversionXml, response.content)
        }
    }

    @Test
    fun `test internal exception from conversion service results in internal server error`() = withBaseTestApplication {
        val internalError = Exception("Conversion error!")
        coEvery { conversionController.convertRequest(allAny()) } throws internalError

        val call = handleRequest(HttpMethod.Post, CONVERSION_ENDPOINT) {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(conversionXml)
        }

        with(call) {
            assertEquals(HttpStatusCode.InternalServerError, response.status())
            assertEquals(quoteWrap(internalError.localizedMessage), response.content)
        }
    }

    @Test
    fun `test sending a request containing no body results in bad request returned`() = withBaseTestApplication {
        val internalError = Exception("Conversion error!")
        coEvery { conversionController.convertRequest(allAny()) } throws internalError

        val call = handleRequest(HttpMethod.Post, CONVERSION_ENDPOINT) {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            // No body
        }

        with(call) {
            assertEquals(HttpStatusCode.BadRequest, response.status())
            assertEquals(quoteWrap("No content for conversion provided in request."), response.content)
        }
    }
}