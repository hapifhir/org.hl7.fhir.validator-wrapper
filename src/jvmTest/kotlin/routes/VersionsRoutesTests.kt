package routes

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import constants.IG_ENDPOINT
import constants.VALIDATION_ENDPOINT
import constants.VERSIONS_ENDPOINT
import io.ktor.http.*
import io.ktor.server.testing.*
import model.FhirVersionsResponse
import model.IGResponse
import org.hl7.fhir.utilities.npm.PackageClient
import org.hl7.fhir.validation.cli.model.CliContext
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class VersionsRoutesTest {

    val expectedVersions = listOf("1.0.2", "1.4.0", "3.0.2", "4.0.1")

    @Test
    fun testGetIgs() = testWithApp {
        handleRequest(HttpMethod.Get, VERSIONS_ENDPOINT).apply {
            assertEquals(HttpStatusCode.OK, response.status())
            val versionsResponse= response.content?.let { ObjectMapper().readValue<FhirVersionsResponse>(it) } ?: fail("Null list of FHIR versions response.")
            assertTrue(versionsResponse.versions.size > expectedVersions.size)
            expectedVersions.forEach {
                assertTrue(versionsResponse.versions.contains(it), "Missing expected version -> $it")
            }
        }
    }
}