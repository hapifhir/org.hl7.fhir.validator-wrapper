package routing.ig

import constants.IG_ENDPOINT
import constants.IG_VERSIONS_ENDPOINT
import constants.VALIDATION_ENDPOINT
import controller.ig.IgController
import controller.ig.NO_IGS_RETURNED
import controller.ig.igModule
import instrumentation.IgInstrumentation.givenAListOfValidIgUrlsA
import instrumentation.IgInstrumentation.givenAListOfValidIgUrlsB
import instrumentation.IgInstrumentation.givenAnEmptyListOfIgUrls
import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.http.ContentType
import io.ktor.server.routing.*
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
        val igResponseA = givenAListOfValidIgUrlsA()
        val igResponseB = givenAListOfValidIgUrlsB()
        coEvery { igController.listIgsFromRegistry() } returns igResponseA
        coEvery { igController.listIgsFromSimplifier() } returns igResponseB


        val call = handleRequest(HttpMethod.Get, IG_ENDPOINT) {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        }

        with(call) {
            assertEquals(HttpStatusCode.OK, response.status())
            val responseBody = response.parseBody(IGResponse::class.java)
            Assertions.assertIterableEquals(igResponseA + igResponseB, responseBody.packageInfo)
        }
    }

    @Test
    fun `when requesting requesting list of valid igs using a partial name, return ig response body`() = withBaseTestApplication {
        val igResponseA = givenAListOfValidIgUrlsA()
        val igResponseB = givenAListOfValidIgUrlsB()
        coEvery { igController.listIgsFromRegistry() } returns igResponseA
        coEvery { igController.listIgsFromSimplifier("dummyIgName") } returns igResponseB


        val call = handleRequest(HttpMethod.Get, "$IG_ENDPOINT?name=dummyIgName") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        }

        with(call) {
            assertEquals(HttpStatusCode.OK, response.status())
            val responseBody = response.parseBody(IGResponse::class.java)
            Assertions.assertIterableEquals(igResponseA + igResponseB, responseBody.packageInfo)
        }
    }

    @Test
    fun `when service provides a list containing 0 items, an internal server error code is returned`() = withBaseTestApplication {
        val igResponseA = givenAnEmptyListOfIgUrls()
        val igResponseB = givenAnEmptyListOfIgUrls()
        coEvery { igController.listIgsFromRegistry() } returns igResponseA
        coEvery { igController.listIgsFromSimplifier() } returns igResponseB

        val call = handleRequest(HttpMethod.Get, IG_ENDPOINT) {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        }

        with(call) {
            assertEquals(HttpStatusCode.InternalServerError, response.status())
            assertEquals(quoteWrap(NO_IGS_RETURNED), response.content)
        }
    }

    @Test
    fun `when requesting requesting list of valid igs for ig versions, return ig response body`() = withBaseTestApplication {
        val igResponseA = givenAListOfValidIgUrlsA()

        coEvery { igController.listIgVersionsFromSimplifier(eq("dummy.package")) } returns igResponseA

        val call = handleRequest(HttpMethod.Get, "${IG_VERSIONS_ENDPOINT}/dummy.package") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        }

        with(call) {
            assertEquals(HttpStatusCode.OK, response.status())
            val responseBody = response.parseBody(IGResponse::class.java)
            Assertions.assertIterableEquals(igResponseA, responseBody.packageInfo)
        }
    }

    @Test
    fun `when service provides a list containing 0 items for ig versions, an internal server error code is returned`() = withBaseTestApplication {
        val igResponseA = givenAnEmptyListOfIgUrls()

        coEvery { igController.listIgVersionsFromSimplifier(eq("dummy.package")) } returns igResponseA

        val call = handleRequest(HttpMethod.Get, "${IG_VERSIONS_ENDPOINT}/dummy.package") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        }

        with(call) {
            assertEquals(HttpStatusCode.InternalServerError, response.status())
            assertEquals(quoteWrap(NO_IGS_RETURNED), response.content)
        }
    }
}