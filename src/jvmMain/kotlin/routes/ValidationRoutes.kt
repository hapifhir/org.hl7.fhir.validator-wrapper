package routes

import constants.VALIDATION_ENDPOINT
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import model.ValidationRequest
import model.ValidationResponse
import org.hl7.fhir.validation.cli.services.ValidationService

fun Route.validationRoutes() {
    post(VALIDATION_ENDPOINT) {
        val logger = call.application.environment.log
        val request = call.receive<ValidationRequest>()
//        request.filesToValidate.forEach {
//            println("request -> " +
//                    "\nFileName -> ${it.fileName}" +
//                    "\nFileContent -> ${it.fileContent}" +
//                    "\nFileType -> ${it.fileType}")
//        }

        val response: ValidationResponse = ValidationService.validateSources(request)
        call.respond(HttpStatusCode.OK, response)
    }

}


