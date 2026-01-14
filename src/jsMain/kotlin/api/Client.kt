package api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.client.plugins.kotlinx.serializer.*
import web.location.location
import kotlinx.serialization.json.*


val endpoint = location.origin + "/" // only needed until https://github.com/ktorio/ktor/issues/1695 is resolved

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