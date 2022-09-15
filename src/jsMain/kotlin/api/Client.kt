package api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.client.plugins.kotlinx.serializer.*
import kotlinx.browser.window
import kotlinx.serialization.json.*


val endpoint = window.location.origin + "/" // only needed until https://github.com/ktorio/ktor/issues/1695 is resolved

val jsonClient = HttpClient (){
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
            useArrayPolymorphism = true
        })
    }
}