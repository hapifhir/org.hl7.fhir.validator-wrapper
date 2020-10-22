package routes

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import constants.IG_ENDPOINT
import io.ktor.http.*
import io.ktor.server.testing.*
import model.IGResponse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class IGRoutesTest {

    val expectedIgs = listOf("http://hl7.org/fhir/us/core/STU3.1.1",
        "http://fhir.org/guides/argonaut/pd/release1",
        "http://hl7.org/fhir/us/davinci-crd/2019May",
        "http://hl7.org.au/fhir")

    @Test
    fun testGetIgs() = testWithApp {
        handleRequest(HttpMethod.Get, IG_ENDPOINT).apply {
            assertEquals(HttpStatusCode.OK, response.status())
            val igResponse= response.content?.let { ObjectMapper().readValue<IGResponse>(it) } ?: fail("Null list of IGs response.")
            assertTrue(igResponse.igs.size > 20)
            expectedIgs.forEach {
                assertTrue(igResponse.igs.contains(it), "Missing expected ig -> $it")
            }
        }
    }
}