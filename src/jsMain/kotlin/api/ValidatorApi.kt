package api

import constants.IG_ENDPOINT
import constants.TERMINOLOGY_ENDPOINT
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


val endpoint = window.location.origin + "/" // only needed until https://github.com/ktorio/ktor/issues/1695 is resolved

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

suspend fun sendValidationRequest(validationRequest: ValidationRequest): ValidationResponse {
    return jsonClient.post(urlString = endpoint + VALIDATION_ENDPOINT) {
        contentType(ContentType.Application.Json)
        body = validationRequest
    }
}

suspend fun sendIGsRequest(): IGResponse {
    return jsonClient.get(urlString = endpoint + IG_ENDPOINT)
}

suspend fun sendVersionsRequest(): FhirVersionsResponse {
    return jsonClient.get(urlString = endpoint + VERSIONS_ENDPOINT)
}

suspend fun validateTxServer(url: String): TerminologyServerResponse {
    return jsonClient.post(urlString = endpoint + TERMINOLOGY_ENDPOINT) {
        contentType(ContentType.Application.Json)
        body = TerminologyServerRequest(url = url)
    }
}

//suspend fun sendDebugMessage(message: String) {
//    val message = jsonClient.post<String>(urlString = endpoint + DEBUG_ENDPOINT) {
//        contentType(ContentType.Application.Json)
//        body = message
//    }
//}