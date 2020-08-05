package api

import constants.VALIDATION_ENDPOINT
import io.ktor.http.*
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import model.*

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
    for (file in validationRequest.getFilesToValidate()) {
        println(file.prettyPrint())
    }

    val message = jsonClient.post<ValidationResponse>(endpoint + VALIDATION_ENDPOINT) {
        contentType(ContentType.Application.Json)
        body = validationRequest
    }

    return message.getOutcomes()
}
