package routes

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.http.*
import kotlin.test.*
import io.ktor.server.testing.*
import org.hl7.fhir.validation.cli.model.CliContext

class ContextRouteTests {
    @Test
    fun testGetContext() = testWithApp {
        val expectedContext = CliContext()
        handleRequest(HttpMethod.Get, "/context").apply {
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals(expectedContext, response.content?.let { ObjectMapper().readValue<CliContext>(it) })
        }
    }

    @Test
    fun testPostContext() = testWithApp {
        val sentContext = CliContext()
        handleRequest(HttpMethod.Post, "/context"){
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(ObjectMapper().writeValueAsString(sentContext))
        }.apply {
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }
}