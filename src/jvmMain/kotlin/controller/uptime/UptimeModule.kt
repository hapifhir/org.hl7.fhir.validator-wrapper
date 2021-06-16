package controller.uptime

import api.terminogy.TerminologyApi
import api.uptime.EndpointApi
import constants.IG_ENDPOINT
import constants.PACKAGES_SERVER_STATUS_ENDPOINT
import constants.TERMINOLOGY_ENDPOINT
import constants.TX_SERVER_STATUS_ENDPOINT
import controller.ig.NO_IGS_RETURNED
import io.ktor.application.*
import io.ktor.client.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import model.IGResponse
import model.TerminologyServerRequest
import model.TerminologyServerResponse
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
