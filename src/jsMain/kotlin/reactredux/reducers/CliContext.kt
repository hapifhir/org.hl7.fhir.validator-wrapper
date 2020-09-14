package reactredux.reducers

import model.CliContext
import reactredux.actions.UpdateContext
import redux.RAction

fun cliContext(state: CliContext = CliContext(), action: RAction): CliContext =
    when (action) {
        is UpdateContext -> {
            action.context
        }
        else -> state
    }