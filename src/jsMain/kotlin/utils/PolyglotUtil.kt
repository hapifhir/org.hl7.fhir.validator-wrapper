package utils

import kotlin.js.Json
import kotlin.js.json

fun getJS(input: Array<Pair<String, Any?>>): Json {
    return json(*input)
}