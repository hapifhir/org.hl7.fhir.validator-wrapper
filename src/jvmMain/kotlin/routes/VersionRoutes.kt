package routes

import constants.VERSIONS_ENDPOINT
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import model.FhirVersionsResponse
import org.hl7.fhir.utilities.VersionUtilities

fun Route.versionRoutes() {

    get(VERSIONS_ENDPOINT) {
        val logger = call.application.environment.log
        val versionsList = VersionUtilities.listSupportedVersions()
            .split(',')
            .map { it.replace("\\s".toRegex(), "") }
            .toMutableList()

        if (versionsList.isEmpty()) {
            call.respond(HttpStatusCode.InternalServerError)
            logger.error("Empty fhir version list returned from core libraries.")
        } else {
            val response = FhirVersionsResponse()
            response.versions = versionsList
            call.respond(HttpStatusCode.OK, response)
        }
    }
}
