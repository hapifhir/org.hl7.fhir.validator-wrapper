package controller.conversion

import constants.CONVERSION_ENDPOINT

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

const val NO_CONTENT_PROVIDED_MESSAGE = "No content for conversion provided in request."
const val INVALID_TYPE_MESSAGE = "Invalid type parameter! Supported xml or json."
const val INVALID_TO_TYPE_MESSAGE = "Invalid toType parameter! Supported xml or json."

fun Route.conversionModule() {

    val conversionController by inject<ConversionController>()

    post(CONVERSION_ENDPOINT) {
        val logger = call.application.environment.log
        val content = call.receiveText()
        val type = call.request.queryParameters["type"]?.lowercase() ?: when {
            call.request.contentType() == ContentType.Application.Xml -> "xml"
            call.request.contentType() == ContentType.Application.Json -> "json"
            else -> "json"
        }
        val version = call.request.queryParameters["version"] ?: "5.0"
        val toVersion = call.request.queryParameters["toVersion"] ?: version
        val toType = call.request.queryParameters["toType"]?.lowercase() ?: type
        var sessionId = call.request.queryParameters["sessionId"] ?: call.request.header("Session-Id")

        logger.info("Received Conversion Request. Convert to $toVersion FHIR version and $toType type. " +
                "Memory (free/max): ${java.lang.Runtime.getRuntime().freeMemory()}/" +
                "${java.lang.Runtime.getRuntime().maxMemory()}")

        when {
            content.isEmpty() -> {
                call.respond(HttpStatusCode.BadRequest, NO_CONTENT_PROVIDED_MESSAGE)
            }
            type != "xml" && type != "json" -> {
                call.respond(HttpStatusCode.BadRequest, INVALID_TYPE_MESSAGE)
            }
            toType != "xml" && toType != "json" -> {
                call.respond(HttpStatusCode.BadRequest, INVALID_TO_TYPE_MESSAGE)
            }

            else -> {
                try {
                    val (response, session) = conversionController.convertRequest(content, type, version, toType,
                        toVersion, sessionId)
                    val contentType = if (toType == "xml") ContentType.Application.Xml else ContentType.Application.Json
                    call.response.headers.append("Session-Id", session)
                    call.respondText(response, contentType, HttpStatusCode.OK)
                } catch (e: Exception) {
                    logger.error(e.localizedMessage, e)
                    call.respond(HttpStatusCode.InternalServerError, e.localizedMessage)
                }
            }
        }
    }
}
