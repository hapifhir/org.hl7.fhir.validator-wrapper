package controller.terminology

import constants.TERMINOLOGY_ENDPOINT
import io.ktor.application.*
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import model.TerminologyServerRequest
import model.TerminologyServerResponse
import org.koin.ktor.ext.inject

const val CONFORMANCE_ENDPOINT = "metadata?_summary=true"

fun Route.terminologyModule() {

    val terminologyController by inject<TerminologyController>()

    post(TERMINOLOGY_ENDPOINT) {
        val logger = call.application.environment.log
        val request = call.receive<TerminologyServerRequest>()
        logger.debug("Checking URL ${request.url} to see if the metadata summary indicates the url implements a terminology server.")
        val urlString =
            if (request.url.endsWith('/')) request.url + CONFORMANCE_ENDPOINT else "${request.url}/$CONFORMANCE_ENDPOINT"
        val client = HttpClient()
        try {
            val htmlContent = client.get<String>(urlString).replace("\\s".toRegex(), "")
            call.respond(HttpStatusCode.OK,
                TerminologyServerResponse(
                    url = request.url,
                    validTxServer = terminologyController.isTerminologyServerValid(htmlContent)
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