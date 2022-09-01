package controller.uptime

import api.uptime.EndpointApi
import io.ktor.client.plugins.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

const val TERM_URL: String = "http://tx.fhir.org"
const val PACK_URL: String = "http://packages2.fhir.org"
const val IMG_URL: String = "/icon-fhir-16.png"

class UptimeControllerImpl : UptimeController, KoinComponent {

    val endpointApi by inject<EndpointApi>()

    override suspend fun isTerminologyServerUp(): Boolean {
        return checkEndpoint(TERM_URL + IMG_URL)
    }

    override suspend fun isPackagesServerUp(): Boolean {
        return checkEndpoint(PACK_URL + IMG_URL)
    }

    suspend fun checkEndpoint(url: String): Boolean {
        return try {
            if (endpointApi.getFileAtEndpoint(url).isEmpty()) {
                return false
            }
            true
        } catch (exception: Exception) {
            false
        }
    }
}