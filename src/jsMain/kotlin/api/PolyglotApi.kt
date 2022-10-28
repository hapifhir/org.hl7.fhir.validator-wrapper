package api


import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.json.JsonObject
import utils.Language


suspend fun getPolyglotPhrases(localeString: String): JsonObject {
    var matched = false
    var newLocaleString = localeString

    for (language in Language.values()) {
        if (localeString == language.code) {
            matched = true
            break
        }
    }
    if (matched == false) {
        for (language in Language.values()) {
            if (localeString == language.getLanguageCode()) {
                newLocaleString = language.code
                break
            }
        }
    }
    val myBody : JsonObject = jsonClient.get(urlString = endpoint + "polyglot/" + newLocaleString + ".json").body()
    return myBody
}