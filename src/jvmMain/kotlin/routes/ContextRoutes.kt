package routes

import constants.CONTEXT_ENDPOINT
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.hl7.fhir.validation.cli.model.CliContext

fun Route.contextRoutes() {
    get (CONTEXT_ENDPOINT) {
        val context = CliContext()
        call.respond(context)
    }

    post (CONTEXT_ENDPOINT) {
        val cliContext = call.receive<CliContext>()
        println("Value received $cliContext")
        call.respond(HttpStatusCode.OK)
    }
}