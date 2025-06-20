package controller.validation

import constants.VALIDATION_ENDPOINT
import constants.VALIDATOR_VERSION_ENDPOINT
import constants.VALIDATION_PRESETS_ENDPOINT

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import model.ValidationRequest
import model.asString
import org.koin.ktor.ext.inject
import utils.badFileEntryExists

const val DEBUG_NUMBER_FILES = "Received %d files to validate."
const val NO_FILES_PROVIDED_MESSAGE = "No files for validation provided in request."
const val INVALID_FILE_MESSAGE = "Improperly formatted file content!"

fun Route.validationModule() {

    val validationController by inject<ValidationController>()

    post(VALIDATION_ENDPOINT) {
        val logger = call.application.environment.log
        val request = call.receive<ValidationRequest>()
        logger.info("Received Validation Request. FHIR Version: ${request.validationContext.sv} IGs: ${request.validationContext.igs} Memory (free/max): ${java.lang.Runtime.getRuntime().freeMemory()}/${java.lang.Runtime.getRuntime().maxMemory()}")
        logger.debug(DEBUG_NUMBER_FILES.format(request.filesToValidate.size))
        request.filesToValidate.forEachIndexed { index, file ->
            logger.debug("file [$index] ->\n${file.asString()}")
        }

        when {
            request.filesToValidate == null || request.filesToValidate.isEmpty() -> {
                call.respond(HttpStatusCode.BadRequest, NO_FILES_PROVIDED_MESSAGE)
            }
            badFileEntryExists(logger, request.filesToValidate) -> {
                call.respond(HttpStatusCode.BadRequest, INVALID_FILE_MESSAGE)
            }
            else -> {
                try {
                    call.respond(HttpStatusCode.OK, validationController.validateRequest(request))
                } catch (e: Exception) {
                    logger.error(e.localizedMessage, e)
                    call.respond(HttpStatusCode.InternalServerError, e.localizedMessage)
                }
            }
        }
    }



    get(VALIDATOR_VERSION_ENDPOINT) {
        call.respond(HttpStatusCode.OK, validationController.getAppVersions())
    }

    get(VALIDATION_PRESETS_ENDPOINT) {
        call.respond(HttpStatusCode.OK, validationController.getValidationPresets())
    }
}
