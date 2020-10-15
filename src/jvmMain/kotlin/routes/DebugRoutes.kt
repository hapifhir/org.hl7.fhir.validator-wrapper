package routes

import constants.CONTEXT_ENDPOINT
import constants.DEBUG_ENDPOINT
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.hl7.fhir.validation.cli.model.CliContext

fun Route.debugRoutes() {
    post (DEBUG_ENDPOINT) {
        val log = call.receive<String>()
        call.respond(HttpStatusCode.OK)
        val logger = call.application.environment.log
        logger.error(log)
    }
}