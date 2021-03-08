package controller.ig

import constants.IG_ENDPOINT
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import model.IGResponse
import org.koin.ktor.ext.inject

const val NO_IGS_RETURNED = "No IGs returned from igController. List size '0'."

fun Route.igModule() {

    val igController by inject<IgController>()

    get(IG_ENDPOINT) {
        val logger = call.application.environment.log
        val urls = igController.listIgs()

        if (urls.size == 0) {
            logger.debug(NO_IGS_RETURNED)
            call.respond(HttpStatusCode.InternalServerError, NO_IGS_RETURNED)
        } else {
            val response = IGResponse(igs = urls)
            call.respond(HttpStatusCode.OK, response)
        }
    }
}
