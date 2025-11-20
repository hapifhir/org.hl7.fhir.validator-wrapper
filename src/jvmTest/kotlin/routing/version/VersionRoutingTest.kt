package routing.version

import constants.VERSIONS_ENDPOINT
import controller.version.NO_SUPPORTED_VERSIONS_RETURNED
import controller.version.VersionController
import controller.version.versionModule
import instrumentation.VersionsInstrumentation.givenAListOfSupportedVersions
import instrumentation.VersionsInstrumentation.givenAnEmptyListOfSupportedVersions
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.routing.*
import io.mockk.coEvery
import io.mockk.mockk
import model.FhirVersionsResponse
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.koin.core.module.Module
import routing.BaseRoutingTest
import kotlin.test.assertEquals

//FIXME remove me after all deprecated withTestApplication tests are removed
class VersionRoutingTest : BaseRoutingTest() {

    private val versionController: VersionController = mockk()

    override fun Module.getKoinModules() {
        single<VersionController> { versionController }
    }

    override fun Routing.getRoutingModules() {
        versionModule()
        // uptimeModule() just add more
    }

    @Test
    fun `when requesting requesting to check a terminology server, return valid terminology response body`() = withBaseTestApplication {
        val versionResponse = givenAListOfSupportedVersions()
        coEvery { versionController.listSupportedVersions() } returns versionResponse

        val response = client.get(VERSIONS_ENDPOINT) {
            contentType(ContentType.Application.Json)
        }
        val responseBody = response.parseBody(FhirVersionsResponse::class.java)
        assertEquals(HttpStatusCode.OK, response.status)
        Assertions.assertIterableEquals(versionResponse, responseBody.versions)
    }

    @Test
    fun `when requesting requesting list of supported versions, return internal server error if list is size empty`() = withBaseTestApplication {
        val versionResponse = givenAnEmptyListOfSupportedVersions()
        coEvery { versionController.listSupportedVersions() } returns versionResponse

        val response = client.get(VERSIONS_ENDPOINT) {
            contentType(ContentType.Application.Json)
        }

        assertEquals(HttpStatusCode.InternalServerError, response.status)
        assertEquals(NO_SUPPORTED_VERSIONS_RETURNED, response.parseBody(String::class.java))
    }


}