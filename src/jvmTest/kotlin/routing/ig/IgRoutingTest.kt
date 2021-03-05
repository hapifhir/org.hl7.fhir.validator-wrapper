package routing.ig

import constants.IG_ENDPOINT
import constants.VALIDATION_ENDPOINT
import controller.ig.IgController
import controller.ig.NO_IGS_RETURNED
import controller.ig.igModule
import instrumentation.IgInstrumentation.givenAListOfValidIgUrls
import instrumentation.IgInstrumentation.givenAnEmptyListOfIgUrls
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.ContentType
import io.ktor.routing.*
import io.ktor.server.testing.*
import io.mockk.coEvery
import io.mockk.mockk
import model.IGResponse
import model.ValidationResponse
import org.junit.jupiter.api.*
import org.koin.dsl.module
import routing.BaseRoutingTest
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IgRoutingTest : BaseRoutingTest() {

    private val igController: IgController = mockk()

    @BeforeAll
    fun setup() {
        koinModules = module {
            single { igController }
        }

        moduleList = {
            install(Routing) {
                igModule()
            }
        }
    }

    @BeforeEach
    fun clearMocks() {
        io.mockk.clearMocks(igController)
    }

    @Test
    fun `when requesting requesting list of valid igs, return ig response body`() = withBaseTestApplication {
        val igResponse = givenAListOfValidIgUrls()
        coEvery { igController.listIgs() } returns igResponse

        val call = handleRequest(HttpMethod.Get, IG_ENDPOINT) {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        }

        with(call) {
            assertEquals(HttpStatusCode.OK, response.status())
            val responseBody = response.parseBody(IGResponse::class.java)
            Assertions.assertIterableEquals(igResponse, responseBody.igs)
        }
    }

    @Test
    fun `when service provides a list containing 0 items, an internal server error code is returned`() = withBaseTestApplication {
        val igResponse = givenAnEmptyListOfIgUrls()
        coEvery { igController.listIgs() } returns igResponse

        val call = handleRequest(HttpMethod.Get, IG_ENDPOINT) {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        }

        with(call) {
            assertEquals(HttpStatusCode.InternalServerError, response.status())
            assertEquals(NO_IGS_RETURNED, response.content)
        }
    }
}