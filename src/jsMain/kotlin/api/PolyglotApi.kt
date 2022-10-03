package api


import io.ktor.client.call.*
import io.ktor.client.request.*


suspend fun getPolyglotPhrases(): String {
    val myBody : String = jsonClient.get(urlString = endpoint + "polyglot/en_US.json").body()
    return myBody
}