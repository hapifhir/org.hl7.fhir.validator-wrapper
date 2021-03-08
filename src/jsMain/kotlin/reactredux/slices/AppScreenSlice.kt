package reactredux.slices

import model.AppScreen
import redux.RAction

object AppScreenSlice {

    data class State(
        val appScreen: AppScreen = AppScreen.VALIDATOR,
    )

    data class SetScreen(val appScreen: AppScreen) : RAction

    fun reducer(state: State = State(), action: RAction): State {
        return when (action) {
            is SetScreen -> state.copy(appScreen = action.appScreen)
            else -> state
        }
    }
}