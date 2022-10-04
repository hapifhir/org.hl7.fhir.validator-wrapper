package api


import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.js.Object
import kotlinx.serialization.json.JsonObject


suspend fun getPolyglotPhrases(): JsonObject {
    val myBody : JsonObject = jsonClient.get(urlString = endpoint + "polyglot/en_US.json").body()
    return myBody
}