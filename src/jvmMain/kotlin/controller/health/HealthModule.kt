package controller.health

import controller.validation.ValidationServiceStatus
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.*

fun Route.healthModule() {
    route("/health/ready") {
        get {
            if (ValidationServiceStatus.isReady()) {
                call.respond(HttpStatusCode.OK, "OK")
            } else {
                call.respond(HttpStatusCode.ServiceUnavailable, "ValidationService not ready")
            }
        }
    }

    route("/health/live") {
        get {
            call.respond(HttpStatusCode.OK, "OK")
        }
    }
}
