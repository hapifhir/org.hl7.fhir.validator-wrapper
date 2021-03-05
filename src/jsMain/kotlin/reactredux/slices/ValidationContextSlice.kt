package reactredux.slices

import model.CliContext
import redux.RAction

object ValidationContextSlice {

    data class State(
        val cliContext: CliContext = CliContext(),
    )

    data class UpdateContext(val cliContext: CliContext) : RAction

    fun reducer(state: State = State(), action: RAction): State {
        return when (action) {
            is UpdateContext -> state.copy(cliContext = action.cliContext)
            else -> state
        }
    }
}