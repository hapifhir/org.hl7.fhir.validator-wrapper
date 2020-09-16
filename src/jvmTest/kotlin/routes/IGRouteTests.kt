package routes

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.http.*
import kotlin.test.*
import io.ktor.server.testing.*
import org.hl7.fhir.validation.cli.model.CliContext

class IGRouteTests {
    @Test
    fun testGetContext() = testWithApp {
        handleRequest(HttpMethod.Get, "/igs").apply {
            assertEquals(HttpStatusCode.OK, response.status())
            val igs = response.content?.let { ObjectMapper().readValue<List<String>>(it) }
            if (igs != null) {
                assertTrue { igs.size > 10 }
                assertTrue { igs.map { ig -> ig.subSequence(0, ig.indexOf('#')) }.toList().contains("hl7.fhir.us.core") }
            } else {
                fail("Fetch igs are null...")
            }
        }
    }
}