package api

import constants.VALIDATION_ENDPOINT
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import model.ValidationOutcome
import model.ValidationRequest
import model.ValidationResponse
import kotlin.browser.window

val endpoint = window.location.origin // only needed until https://github.com/ktorio/ktor/issues/1695 is resolved

val nonStrict = Json(
    JsonConfiguration(
        isLenient = true,
        ignoreUnknownKeys = true,
        serializeSpecialFloatingPointValues = true,
        useArrayPolymorphism = true
    )
)

val jsonClient = HttpClient {
    install(JsonFeature) {
        serializer = KotlinxSerializer(nonStrict)
    }
}

suspend fun sendValidationRequest(validationRequest: ValidationRequest): List<ValidationOutcome> {
    val message = jsonClient.post<ValidationResponse>(endpoint + VALIDATION_ENDPOINT) {
        contentType(ContentType.Application.Json)
        body = validationRequest
    }

    return message.getOutcomes()
}
