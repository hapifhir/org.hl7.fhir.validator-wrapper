package routing.terminology

import api.terminogy.TerminologyApi
import constants.TERMINOLOGY_ENDPOINT
import controller.terminology.TerminologyController
import controller.terminology.terminologyModule
import instrumentation.TerminologyInstrumentation.givenATerminologyServerUrl
import instrumentation.TerminologyInstrumentation.givenAValidCapabilityStatement
import instrumentation.TerminologyInstrumentation.givenAnInvalidCapabilityStatement
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.routing.*
import io.mockk.coEvery
import io.mockk.mockk
import model.TerminologyServerRequest
import model.TerminologyServerResponse

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.koin.core.module.Module
import routing.BaseRoutingTest
import kotlin.test.assertEquals

class TerminologyRoutingTest: BaseRoutingTest() {

    private val terminologyController: TerminologyController = mockk()
    private val terminologyApi: TerminologyApi = mockk()

    override fun Module.getKoinModules() {
        single<TerminologyController> { terminologyController }
        single<TerminologyApi> { terminologyApi }
    }

    override fun Routing.getRoutingModules() {
        terminologyModule()
    }

    @Test
    fun `when requesting requesting to check a terminology server, return valid terminology response body` () = withBaseTestApplication {
        val url = givenATerminologyServerUrl()
        val terminologyServerRequest = TerminologyServerRequest(url = url)
        coEvery { terminologyApi.getCapabilityStatement(any()) } returns givenAValidCapabilityStatement()
        coEvery { terminologyController.isTerminologyServerValid(any()) } returns true

        val response = client.post(TERMINOLOGY_ENDPOINT) {
            contentType(ContentType.Application.Json)
            setBody(toJsonBody(terminologyServerRequest))
        }

        assertEquals(HttpStatusCode.OK, response.status)
        val responseBody = response.parseBody(model.TerminologyServerResponse::class.java)
        Assertions.assertEquals(url, responseBody.url)
        Assertions.assertEquals(true, responseBody.validTxServer)
    }

    @Test
    fun `when requesting to check bad terminology server, response with bad terminology is returned`() =
    withBaseTestApplication {
        val url = givenATerminologyServerUrl()
        val terminologyServerRequest = TerminologyServerRequest(url = url)
        coEvery { terminologyApi.getCapabilityStatement(any()) } returns givenAnInvalidCapabilityStatement()
        coEvery { terminologyController.isTerminologyServerValid(any()) } returns false

        val response = client.post(TERMINOLOGY_ENDPOINT) {
            contentType(ContentType.Application.Json)
            setBody(toJsonBody(terminologyServerRequest))
        }

        assertEquals(HttpStatusCode.OK, response.status)
        val responseBody = response.parseBody(TerminologyServerResponse::class.java)
        Assertions.assertEquals(url, responseBody.url)
        Assertions.assertEquals(false, responseBody.validTxServer)
    }
}