package api

import constants.DEBUG_ENDPOINT
import constants.IG_ENDPOINT
import constants.VALIDATION_ENDPOINT
import constants.VERSIONS_ENDPOINT
import io.ktor.client.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.browser.window
import kotlinx.serialization.json.Json
import model.*

val endpoint = window.location.origin // only needed until https://github.com/ktorio/ktor/issues/1695 is resolved

const val CONFORMANCE_ENDPOINT = "metadata?_summary=true"

const val XML_CAP_STMT_TX = "<instantiates value=\"http://hl7.org/fhir/CapabilityStatement/terminology-server\"/>"
const val JSON_CAP_STMT_TX = "\"instantiates\":\"http://hl7.org/fhir/CapabilityStatement/terminology-server\""

val jsonClient = HttpClient {
    install(JsonFeature) {
        serializer = KotlinxSerializer(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
            useArrayPolymorphism = true
        })
    }
}

suspend fun sendValidationRequest(validationRequest: ValidationRequest): List<ValidationOutcome> {
    val message = jsonClient.post<ValidationResponse>(urlString = endpoint + VALIDATION_ENDPOINT) {
        contentType(ContentType.Application.Json)
        body = validationRequest
    }
    return message.getOutcomes().map { it.setValidated(true) }
}

suspend fun sendIGsRequest(): IGResponse {
    return jsonClient.get(urlString = endpoint + IG_ENDPOINT)
}

suspend fun sendVersionsRequest(): FhirVersionsResponse {
    return jsonClient.get(urlString = endpoint + VERSIONS_ENDPOINT)
}

suspend fun validateTxServer(url: String): Boolean {
    var response: String = jsonClient.get(urlString = if (url.endsWith('/')) url + CONFORMANCE_ENDPOINT else "$url/$CONFORMANCE_ENDPOINT")
    /*
     * We take the returned capability statement as a big string, remove all the whitespace, and search for the
     * matching capability statement we want.
     */
    response = response.replace("\\s".toRegex(), "")
    return (response.contains(XML_CAP_STMT_TX) || response.contains(JSON_CAP_STMT_TX))
}

//suspend fun sendDebugMessage(message: String) {
//    val message = jsonClient.post<String>(urlString = endpoint + DEBUG_ENDPOINT) {
//        contentType(ContentType.Application.Json)
//        body = message
//    }
//}