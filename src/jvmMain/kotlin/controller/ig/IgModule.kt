package controller.ig

import constants.IG_ENDPOINT
import constants.IG_VERSIONS_ENDPOINT
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

import model.IGResponse
import model.PackageInfo
import org.koin.ktor.ext.inject

const val NO_IGS_RETURNED = "No IGs returned from igController. List size is 0."




fun Route.igModule() {

    val igController by inject<IgController>()

    get(IG_ENDPOINT) {
        val logger = call.application.environment.log

        val igsFromRegistry =  igController.listIgsFromRegistry()

        val name = call.parameters["name"]


        val igsFromSimplifier = if (name == null) {igController.listIgsFromSimplifier() } else { igController.listIgsFromSimplifier(name) }

        val packageInfo  = (igsFromRegistry + igsFromSimplifier).toMutableList()

        if (packageInfo.size == 0) {
            logger.debug(NO_IGS_RETURNED)
            call.respond(HttpStatusCode.InternalServerError, NO_IGS_RETURNED)
        } else {
            val response = IGResponse(packageInfo = packageInfo)
            call.respond(HttpStatusCode.OK, response)
        }
    }

    get("${IG_VERSIONS_ENDPOINT}/{igPackageName}") {
        val logger = call.application.environment.log

        val igPackageName = call.parameters["igPackageName"]
        val packageInfo = igController.listIgVersionsFromSimplifier(igPackageName)
        if (packageInfo.size == 0) {
            logger.debug(NO_IGS_RETURNED)
            call.respond(HttpStatusCode.InternalServerError, NO_IGS_RETURNED)
        } else {
            val response = IGResponse(packageInfo = packageInfo)
            call.respond(HttpStatusCode.OK, response)
        }
    }
}
