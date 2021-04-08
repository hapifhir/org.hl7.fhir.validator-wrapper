package routing.terminology

import constants.TERMINOLOGY_ENDPOINT
import controller.terminology.TerminologyController
import controller.terminology.terminologyModule
import instrumentation.TerminologyInstrumentation.givenATerminologyServerUrl
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.ContentType
import io.ktor.routing.*
import io.ktor.server.testing.*
import io.mockk.coEvery
import io.mockk.mockk
import model.TerminologyServerRequest
import model.TerminologyServerResponse
import org.junit.jupiter.api.*
import org.koin.dsl.module
import routing.BaseRoutingTest
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TerminologyRoutingTest : BaseRoutingTest() {

    private val terminologyController: TerminologyController = mockk()

    @BeforeAll
    fun setup() {
        koinModules = module {
            single { terminologyController }
        }

        moduleList = {
            install(Routing) {
                terminologyModule()
            }
        }
    }

    @BeforeEach
    fun clearMocks() {
        io.mockk.clearMocks(terminologyController)
    }

    @Test
    fun `when requesting requesting to check a terminology server, return valid terminology response body`() =
        withBaseTestApplication {
            val url = givenATerminologyServerUrl()
            val terminologyServerRequest = TerminologyServerRequest(url = url)
            coEvery { terminologyController.isTerminologyServerValid(any()) } returns true

            val call = handleRequest(HttpMethod.Post, TERMINOLOGY_ENDPOINT) {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(toJsonBody(terminologyServerRequest))
            }

            with(call) {
                assertEquals(HttpStatusCode.OK, response.status())
                val responseBody = response.parseBody(TerminologyServerResponse::class.java)
                Assertions.assertEquals(url, responseBody.url)
                // TODO move network code that pull conformance statement to it's own service, then mockk that service for testing
//            Assertions.assertEquals(true, responseBody.validTxServer)
            }
        }

    @Test
    fun `when requesting to check bad terminology server, response with bad terminology is returned`() =
        withBaseTestApplication {
            val url = givenATerminologyServerUrl()
            val terminologyServerRequest = TerminologyServerRequest(url = url)
            coEvery { terminologyController.isTerminologyServerValid(any()) } returns false

            val call = handleRequest(HttpMethod.Post, TERMINOLOGY_ENDPOINT) {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(toJsonBody(terminologyServerRequest))
            }

            with(call) {
                assertEquals(HttpStatusCode.OK, response.status())
                val responseBody = response.parseBody(TerminologyServerResponse::class.java)
                Assertions.assertEquals(url, responseBody.url)
                // TODO move network code that pull conformance statement to it's own service, then mockk that service for testing
//            Assertions.assertEquals(false, responseBody.validTxServer)
            }
        }
}