package reactredux.thunk

import api.getValidationPresets
import Polyglot
import kotlinx.coroutines.*
import reactredux.store.AppState
import reactredux.store.RThunk
import reactredux.store.nullAction
import redux.RAction
import redux.WrapperAction

import reactredux.slices.PresetsSlice

class FetchPresetsThunk () : RThunk {

    override fun invoke(dispatch: (RAction) -> WrapperAction, getState: () -> AppState): WrapperAction {
        GlobalScope.launch {
            val presets = getValidationPresets()
            dispatch(PresetsSlice.SetPresets(presets))
        }
        return nullAction
    }
}