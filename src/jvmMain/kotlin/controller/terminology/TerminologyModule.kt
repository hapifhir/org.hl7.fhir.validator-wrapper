package controller.terminology

import api.terminogy.TerminologyApi
import constants.TERMINOLOGY_ENDPOINT

import io.ktor.client.plugins.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import model.TerminologyServerRequest
import model.TerminologyServerResponse
import org.koin.ktor.ext.inject

fun Route.terminologyModule() {

    val terminologyController by inject<TerminologyController>()
    val terminologyApi by inject<TerminologyApi>()

    post(TERMINOLOGY_ENDPOINT) {
        val logger = call.application.environment.log
        val request = call.receive<TerminologyServerRequest>()
        logger.debug("Checking URL ${request.url} to see if the metadata summary indicates the url implements a terminology server.")
        try {
            call.respond(HttpStatusCode.OK,
                TerminologyServerResponse(
                    url = request.url,
                    validTxServer = terminologyController.isTerminologyServerValid(terminologyApi.getCapabilityStatement(
                        request.url))
                )
            )
        } catch (exception: ClientRequestException) {
            call.respond(HttpStatusCode.OK, TerminologyServerResponse(
                url = request.url,
                validTxServer = false,
                details = exception.localizedMessage)
            )
        }
    }
}// https://r4.ontoserver.csiro.au/fhir/
