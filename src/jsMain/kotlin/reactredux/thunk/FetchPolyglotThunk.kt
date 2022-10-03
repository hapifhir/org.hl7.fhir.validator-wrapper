package reactredux.thunk

import Polyglot
import kotlinx.browser.window
import reactredux.slices.LocalizationSlice
import reactredux.store.AppState
import reactredux.store.RThunk
import reactredux.store.nullAction
import redux.RAction
import redux.WrapperAction

class FetchPolyglotThunk : RThunk {

    fun fetchPolyglot ()  : Polyglot {
        console.log("hello fetchPolyglot")
        var polyglot = Polyglot(js("{locale: \"es\"}"))
        polyglot.extend(phrases = js("{" +
                "'Validate': '¡Validar!'," +
                "'Options': 'Opciones'" +
                "}"))
        return polyglot
    }


    override fun invoke(dispatch: (RAction) -> WrapperAction, getState: () -> AppState): WrapperAction {


        window.setTimeout({
                dispatch(LocalizationSlice.SetPolyglot(fetchPolyglot()))
        }, 5000)

        return nullAction
    }
}