package routing.version

import constants.VERSIONS_ENDPOINT
import controller.version.NO_SUPPORTED_VERSIONS_RETURNED
import controller.version.VersionController
import controller.version.versionModule
import instrumentation.VersionsInstrumentation.givenAListOfSupportedVersions
import instrumentation.VersionsInstrumentation.givenAnEmptyListOfSupportedVersions
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.ContentType
import io.ktor.routing.*
import io.ktor.server.testing.*
import io.mockk.coEvery
import io.mockk.mockk
import model.FhirVersionsResponse
import org.junit.jupiter.api.*
import org.koin.dsl.module
import routing.BaseRoutingTest
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VersionRoutingTest : BaseRoutingTest() {

    private val versionController: VersionController = mockk()

    @BeforeAll
    fun setup() {
        koinModules = module {
            single { versionController }
        }

        moduleList = {
            install(Routing) {
                versionModule()
            }
        }
    }

    @BeforeEach
    fun clearMocks() {
        io.mockk.clearMocks(versionController)
    }

    @Test
    fun `when requesting requesting list of versions, return fhir versions response body`() = withBaseTestApplication {
        val versionResponse = givenAListOfSupportedVersions()
        coEvery { versionController.listSupportedVersions() } returns versionResponse

        val call = handleRequest(HttpMethod.Get, VERSIONS_ENDPOINT) {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        }

        with(call) {
            assertEquals(HttpStatusCode.OK, response.status())
            val responseBody = response.parseBody(FhirVersionsResponse::class.java)
            Assertions.assertIterableEquals(versionResponse, responseBody.versions)
        }
    }

    @Test
    fun `when requesting requesting list of supported versions, return internal server error if list is size empty`() = withBaseTestApplication {
            val versionResponse = givenAnEmptyListOfSupportedVersions()
            coEvery { versionController.listSupportedVersions() } returns versionResponse

            val call = handleRequest(HttpMethod.Get, VERSIONS_ENDPOINT) {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            }

            with(call) {
                assertEquals(HttpStatusCode.InternalServerError, response.status())
                assertEquals(NO_SUPPORTED_VERSIONS_RETURNED, response.content)
            }
        }
}