package api


import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.json.JsonObject


suspend fun getPolyglotPhrases(localeString: String): JsonObject {
    val myBody : JsonObject = jsonClient.get(urlString = endpoint + "polyglot/" + localeString + ".json").body()
    return myBody
}