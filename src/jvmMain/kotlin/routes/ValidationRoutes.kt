package routes

import constants.VALIDATION_ENDPOINT
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import model.*
import org.hl7.fhir.validation.cli.services.ValidationService
import org.slf4j.Logger

const val DEBUG_NUMBER_FILES = "Received %d files to validate."
const val NO_FILES_PROVIDED_MESSAGE = "No files for validation provided in request."
const val INVALID_FILE_MESSAGE = "Improperly formatted FileInfo passed in with request."
const val INVALID_FILE = "Invalid file entry passed in -> %s"

fun Route.validationRoutes() {
    post(VALIDATION_ENDPOINT) {
        val logger = call.application.environment.log
        val request = call.receive<ValidationRequest>()

        logger.debug(DEBUG_NUMBER_FILES.format(request.filesToValidate.size))
        request.filesToValidate.forEachIndexed { index, file ->
            logger.debug("file [$index] ->\n${file.asString()}")
        }
        if (request.filesToValidate.isEmpty()) {
            call.respond(HttpStatusCode.BadRequest, NO_FILES_PROVIDED_MESSAGE)
        } else if (badFileEntryExists(logger, request.filesToValidate)) {
            call.respond(HttpStatusCode.BadRequest, INVALID_FILE_MESSAGE)
        } else {
            try {
                val response: ValidationResponse = ValidationService.validateSources(request)
                call.respond(HttpStatusCode.OK, response)
            } catch (e: Exception) {
                logger.error(e.localizedMessage)
                call.respond(HttpStatusCode.InternalServerError, e.localizedMessage)
            }
        }
    }
}

fun badFileEntryExists(logger: Logger, filesToValidate: MutableCollection<FileInfo>): Boolean {
    return filesToValidate.filterNot { it.isValid() }
        .onEach { logger.error(INVALID_FILE.format(it.asString())) }
        .isNotEmpty()
}





