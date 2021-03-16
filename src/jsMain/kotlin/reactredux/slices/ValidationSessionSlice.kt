package reactredux.slices

import redux.RAction

object ValidationSessionSlice {

    data class State(
        val sessionId: String = "",
    )

    data class SetSessionId(val sessionId: String) : RAction

    fun reducer(state: State = State(), action: RAction): State {
        return when (action) {
            is SetSessionId -> {
                state.copy(sessionId = action.sessionId)
            }
            else -> state
        }
    }
}