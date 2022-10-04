package reactredux.thunk

import Polyglot
import kotlinx.coroutines.*
import reactredux.slices.LocalizationSlice
import reactredux.store.AppState
import reactredux.store.RThunk
import reactredux.store.nullAction
import redux.RAction
import redux.WrapperAction

import api.getPolyglotPhrases

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlin.js.json

class FetchPolyglotThunk : RThunk {


    override fun invoke(dispatch: (RAction) -> WrapperAction, getState: () -> AppState): WrapperAction {

        GlobalScope.launch {
            val phrases : JsonObject = getPolyglotPhrases()

            /*  Polyglot expects js or json as its phrases, so we need to convert our
                kotlin JsonObject into the Pair<String, Any?> structure that the json
                method will accept.
             */
            val pairs : Array<Pair<String, Any?>> = phrases.entries.map {
                   it -> Pair(it.key, (it.value as JsonPrimitive).content)
            }.toTypedArray()

            var polyglot = Polyglot(js("{locale: \"en\"}"))
            polyglot.extend(phrases = json(*pairs))

            dispatch(LocalizationSlice.SetPolyglot(polyglot))
        }
        return nullAction
    }
}