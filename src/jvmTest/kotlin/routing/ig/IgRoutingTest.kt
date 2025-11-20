package routing.ig

import constants.IG_ENDPOINT
import constants.IG_VERSIONS_ENDPOINT
import controller.ig.IgController
import controller.ig.NO_IGS_RETURNED
import controller.ig.igModule
import instrumentation.IgInstrumentation.givenAListOfValidIgUrlsA
import instrumentation.IgInstrumentation.givenAListOfValidIgUrlsB
import instrumentation.IgInstrumentation.givenAnEmptyListOfIgUrls
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.routing.*
import io.mockk.coEvery
import io.mockk.mockk
import model.IGResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test
import org.koin.core.module.Module
import routing.BaseRoutingTest

class IgRoutingTest: BaseRoutingTest() {

    private val igController: IgController = mockk()

    override fun Module.getKoinModules() {
        single<IgController> { igController }
    }

    override fun Routing.getRoutingModules() {
        igModule()
    }

    @Test
    fun `when requesting requesting list of valid igs, return ig response body`() = withTestApplication {
        val igResponseA = givenAListOfValidIgUrlsA()
        val igResponseB = givenAListOfValidIgUrlsB()
        coEvery { igController.listIgsFromRegistry() } returns igResponseA
        coEvery { igController.listIgsFromSimplifier() } returns igResponseB


        val response = client.get(IG_ENDPOINT) {
            contentType(ContentType.Application.Json)
        }

        assertEquals(HttpStatusCode.OK, response.status)
        val responseBody = response.parseBody(IGResponse::class.java)
        assertIterableEquals(igResponseA + igResponseB, responseBody.packageInfo)
    }

    @Test
    fun `when requesting requesting list of valid igs using a partial name, return ig response body`() = withTestApplication {
        val igResponseA = givenAListOfValidIgUrlsA()
        val igResponseB = givenAListOfValidIgUrlsB()
        coEvery { igController.listIgsFromRegistry() } returns igResponseA
        coEvery { igController.listIgsFromSimplifier("dummyIgName") } returns igResponseB

        val response = client.get( "$IG_ENDPOINT?name=dummyIgName") {
            contentType(ContentType.Application.Json)
        }

        assertEquals(HttpStatusCode.OK, response.status)
        val responseBody = response.parseBody(IGResponse::class.java)
        assertIterableEquals(igResponseA + igResponseB, responseBody.packageInfo)
    }

    @Test
    fun `when service provides a list containing 0 items, an internal server error code is returned`() = withTestApplication {
        val igResponseA = givenAnEmptyListOfIgUrls()
        val igResponseB = givenAnEmptyListOfIgUrls()
        coEvery { igController.listIgsFromRegistry() } returns igResponseA
        coEvery { igController.listIgsFromSimplifier() } returns igResponseB

        val response = client.get(IG_ENDPOINT) {
            contentType(ContentType.Application.Json)
        }

        assertEquals(HttpStatusCode.InternalServerError, response.status)
        assertEquals(NO_IGS_RETURNED, response.parseBody(String::class.java))
    }

    @Test
    fun `when requesting requesting list of valid igs for ig versions, return ig response body`() = withTestApplication {
        val igResponseA = givenAListOfValidIgUrlsA()

        coEvery { igController.listIgVersionsFromSimplifier(eq("dummy.package")) } returns igResponseA

        val response = client.get( "${IG_VERSIONS_ENDPOINT}/dummy.package") {
            contentType(ContentType.Application.Json)
        }

        assertEquals(HttpStatusCode.OK, response.status)
        val responseBody = response.parseBody(IGResponse::class.java)
        assertIterableEquals(igResponseA, responseBody.packageInfo)
    }

    @Test
    fun `when service provides a list containing 0 items for ig versions, an internal server error code is returned`() = withTestApplication {
        val igResponseA = givenAnEmptyListOfIgUrls()

        coEvery { igController.listIgVersionsFromSimplifier(eq("dummy.package")) } returns igResponseA

        val response = client.get( "${IG_VERSIONS_ENDPOINT}/dummy.package") {
            contentType(ContentType.Application.Json)
        }

        assertEquals(HttpStatusCode.InternalServerError, response.status)
        assertEquals(NO_IGS_RETURNED, response.parseBody(String::class.java))
    }
}