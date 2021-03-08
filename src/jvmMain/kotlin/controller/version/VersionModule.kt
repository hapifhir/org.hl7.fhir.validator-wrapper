package controller.version

import constants.VERSIONS_ENDPOINT
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import model.FhirVersionsResponse
import org.koin.ktor.ext.inject

const val NO_SUPPORTED_VERSIONS_RETURNED = "Empty fhir version list returned from core libraries."

fun Route.versionModule() {

    val versionController by inject<VersionController>()

    get(VERSIONS_ENDPOINT) {
        val logger = call.application.environment.log
        val versionsList = versionController.listSupportedVersions()

        if (versionsList.isEmpty()) {
            logger.error(NO_SUPPORTED_VERSIONS_RETURNED)
            call.respond(HttpStatusCode.InternalServerError, NO_SUPPORTED_VERSIONS_RETURNED)
        } else {
            call.respond(HttpStatusCode.OK, FhirVersionsResponse(versions = versionsList))
        }
    }
}