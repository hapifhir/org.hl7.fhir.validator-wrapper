package controller.uptime

import constants.PACKAGES_SERVER_STATUS_ENDPOINT
import constants.TX_SERVER_STATUS_ENDPOINT
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

const val TX_DOWN = "Terminology server is down."
const val PACK_DOWN = "Package server is down."

fun Route.uptimeModule() {

    val uptimeController by inject<UptimeController>()

    get(TX_SERVER_STATUS_ENDPOINT) {
        val logger = call.application.environment.log
        val up = uptimeController.isTerminologyServerUp()

        if (up) {
            call.respond(HttpStatusCode.OK, true)
        } else {
            logger.debug(TX_DOWN)
            call.respond(HttpStatusCode.OK, false)
        }
    }

    get(PACKAGES_SERVER_STATUS_ENDPOINT) {
        val logger = call.application.environment.log
        val up = uptimeController.isPackagesServerUp()

        if (up) {
            call.respond(HttpStatusCode.OK, true)
        } else {
            logger.debug(PACK_DOWN)
            call.respond(HttpStatusCode.OK, false)
        }
    }
}
