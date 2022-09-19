package controller.debug

import constants.DEBUG_ENDPOINT
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.debugModule() {

    /**
     * At any point, if we want to send a debug message from the front end to the back end, or do any kind of testing
     * that forces a log out on the server, we can use this endpoint. Once the project get released as a 1.0.0, this
     * will be removed.
     */
    post(DEBUG_ENDPOINT) {
        val log = call.receive<String>()
        call.respond(HttpStatusCode.OK)
        val logger = call.application.environment.log
        logger.error(log)
    }
}