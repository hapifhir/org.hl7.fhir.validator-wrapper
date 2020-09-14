package reactredux.reducers

import reactredux.actions.ToggleValidating
import redux.RAction

fun validationInProgress(state: Boolean = false, action: RAction): Boolean =
    when (action) {
        is ToggleValidating -> {
            action.validating
        }
        else -> state
    }