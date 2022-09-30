package reactredux.thunk

import Polyglot
import kotlinx.browser.window
import reactredux.slices.LocalizationSlice
import reactredux.store.AppState
import reactredux.store.RThunk
import reactredux.store.nullAction
import redux.RAction
import redux.WrapperAction

class FetchLocalizedStringThunk : RThunk {

    fun getPolyglot ()  : Polyglot {
        console.log("getPolyglot")
        var polyglot = Polyglot(js("{locale: \"fr\"}"))
        polyglot.extend(phrases = js("{" +
                "'heading_validate': 'Boop a doo'," +
                "'test_string': 'Test String'" +
                "}"))
        return polyglot
    }


    override fun invoke(dispatch: (RAction) -> WrapperAction, getState: () -> AppState): WrapperAction {

        window.setTimeout({


                dispatch(LocalizationSlice.SetPolyglot(getPolyglot()))

        }, 2000)

        return nullAction
    }
}