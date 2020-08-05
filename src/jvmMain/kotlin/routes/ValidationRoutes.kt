package routes

import constants.VALIDATION_ENDPOINT
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import model.ValidationRequest
import org.hl7.fhir.validation.cli.services.ValidationService
import validationEngine

fun Route.validationRoutes() {

    post (VALIDATION_ENDPOINT) {
        val logger = call.application.environment.log
        val request = call.receive<ValidationRequest>()
        val response = ValidationService.validateSources(request, validationEngine)

        if (response == null) {
            call.respond(HttpStatusCode.InternalServerError)
        } else {
            call.respond(HttpStatusCode.OK, response)
        }
    }

}
