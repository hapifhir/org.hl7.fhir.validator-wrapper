package api

import constants.VALIDATION_ENDPOINT
import io.ktor.client.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.browser.window
import kotlinx.serialization.json.Json
import model.ValidationOutcome
import model.ValidationRequest
import model.ValidationResponse

val endpoint = window.location.origin // only needed until https://github.com/ktorio/ktor/issues/1695 is resolved

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
    val message = jsonClient.post<ValidationResponse>(endpoint + VALIDATION_ENDPOINT) {
        contentType(ContentType.Application.Json)
        body = validationRequest
    }

    return message.getOutcomes()
}
