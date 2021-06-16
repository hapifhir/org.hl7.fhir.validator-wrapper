package api

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import kotlinx.browser.window
import kotlinx.serialization.json.Json

val endpoint = window.location.origin + "/" // only needed until https://github.com/ktorio/ktor/issues/1695 is resolved

val jsonClient = HttpClient {
    install(JsonFeature) {
        serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
            useArrayPolymorphism = true
        })
    }
}