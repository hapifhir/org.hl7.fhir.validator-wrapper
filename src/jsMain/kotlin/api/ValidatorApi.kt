package api

import constants.IG_ENDPOINT
import constants.IG_VERSIONS_ENDPOINT
import constants.TERMINOLOGY_ENDPOINT
import constants.VALIDATION_ENDPOINT
import constants.VERSIONS_ENDPOINT

import io.ktor.client.request.*
import io.ktor.http.*

import model.*

suspend fun sendValidationRequest(validationRequest: ValidationRequest): ValidationResponse {
    return jsonClient.post(urlString = endpoint + VALIDATION_ENDPOINT) {
        contentType(ContentType.Application.Json)
        body = validationRequest
    }
}

suspend fun sendIGsRequest(): IGResponse {
    return jsonClient.get(urlString = endpoint + IG_ENDPOINT)
}

suspend fun sendIGVersionsRequest(igPackageName : String) : IGResponse {
    return jsonClient.get(urlString = "$endpoint$IG_VERSIONS_ENDPOINT/${igPackageName}")
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