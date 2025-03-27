package reactredux.slices


import model.Preset
import reactredux.store.RThunk
import reactredux.thunk.FetchPresetsThunk
import redux.RAction

object PresetsSlice {

    data class State(
        val presets: List<Preset> = emptyList()
        )

    fun fetchPresets(): RThunk {
        return FetchPresetsThunk()
    }

    data class SetPresets(val presets: List<Preset>) : RAction

    fun reducer(state: State = State(), action: RAction): State {
        return when (action) {
            is SetPresets -> {
                state.copy(presets = action.presets)
            }
            else -> state
        }
    }
}