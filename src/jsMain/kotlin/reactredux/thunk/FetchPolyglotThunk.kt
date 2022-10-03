package reactredux.thunk

import Polyglot
import kotlinx.coroutines.*
import kotlinx.browser.window
import reactredux.slices.LocalizationSlice
import reactredux.store.AppState
import reactredux.store.RThunk
import reactredux.store.nullAction
import redux.RAction
import redux.WrapperAction

import api.getPolyglotPhrases
import kotlinext.js.asJsObject

import kotlinx.serialization.json.Json
class FetchPolyglotThunk : RThunk {


    override fun invoke(dispatch: (RAction) -> WrapperAction, getState: () -> AppState): WrapperAction {

        GlobalScope.launch {
            val phrases : String = getPolyglotPhrases()
            console.log("hello fetchPolyglot")
            console.log("phrases: " + phrases)
            var polyglot = Polyglot(js("{locale: \"es\"}"))
           polyglot.extend(phrases = JSON.parse(phrases))


            dispatch(LocalizationSlice.SetPolyglot(polyglot))
        }
        return nullAction
    }
}