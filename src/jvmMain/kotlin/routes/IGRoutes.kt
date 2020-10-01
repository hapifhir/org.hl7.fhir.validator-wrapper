package routes

import constants.IG_ENDPOINT
import constants.VALIDATION_ENDPOINT
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*

fun Route.igRoutes() {

    get(IG_ENDPOINT) {
        val logger = call.application.environment.log
        val response = listOf("address1", "address2", "address3", "address4")
//        val response = ValidationService.validateSources(request, validationEngine)

//        if (response == null) {
//            call.respond(HttpStatusCode.InternalServerError)
//        } else {
        call.respond(HttpStatusCode.OK, response)
//        }
    }

}
