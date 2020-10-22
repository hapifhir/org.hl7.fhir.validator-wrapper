package routes

import constants.IG_ENDPOINT
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import model.IGResponse
import org.hl7.fhir.utilities.npm.PackageClient

const val PACKAGE_CLIENT_ADDRESS = "https://packages.fhir.org"

fun Route.igRoutes() {

    get(IG_ENDPOINT) {
        val logger = call.application.environment.log
        val packageList = PackageClient(PACKAGE_CLIENT_ADDRESS).listFromRegistry(null, null, null)
        val urls = packageList.map {
            it.url
        }.toMutableList()

        if (urls.size == 0) {
            call.respond(HttpStatusCode.InternalServerError)
        } else {
            val response = IGResponse()
            response.igs = urls
            call.respond(HttpStatusCode.OK, response)
        }
    }
}
