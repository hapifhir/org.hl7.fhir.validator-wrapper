import constants.VERSIONS_ENDPOINT
import controller.version.VersionController
import controller.version.versionModule
import instrumentation.VersionsInstrumentation.givenAListOfSupportedVersions
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.routing.*
import io.mockk.coEvery
import io.mockk.mockk
import model.FhirVersionsResponse
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.koin.core.module.Module
import kotlin.test.assertEquals

//FIXME remove me after all deprecated withTestApplication tests are removed
class SanityCheckRoutingTest : BaseRoutingTest() {

    private val versionController: VersionController = mockk()

    override fun Module.getKoinModules() {
        single<VersionController> { versionController }
    }

    override fun Routing.getRoutingModules() {
        versionModule()
        // uptimeModule() just add more
    }

    @Test
    fun `when requesting requesting to check a terminology server, return valid terminology response body`() = testApplication {
        val versionResponse = givenAListOfSupportedVersions()
        coEvery { versionController.listSupportedVersions() } returns versionResponse

        val response = client.get(VERSIONS_ENDPOINT) {
            contentType(ContentType.Application.Json)
        }
        val responseBody = response.parseBody(FhirVersionsResponse::class.java)
        assertEquals(HttpStatusCode.OK, response.status)
        Assertions.assertIterableEquals(versionResponse, responseBody.versions)
    }




}