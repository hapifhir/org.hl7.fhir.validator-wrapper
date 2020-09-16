package routes

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import org.hl7.fhir.utilities.cache.FilesystemPackageCacheManager

fun Route.igRoutes() {

    // Tools version not currently used by libraries
    val manager = FilesystemPackageCacheManager(true, -1)
    get("/igs") {
        call.respond(manager.listPackages())
    }
}