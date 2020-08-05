package routes

import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.hl7.fhir.validation.cli.model.CliContext

fun Route.contextRoutes() {

    get ("/context") {
        val context = CliContext()
        call.respond(context)
    }

    post ("/context") {
        val cliContext = call.receive<CliContext>()
        println("Value received $cliContext")
        call.respond(HttpStatusCode.OK)
    }

}