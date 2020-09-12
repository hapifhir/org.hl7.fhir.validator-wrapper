package reactredux.reducers

import model.AppScreen
import model.CliContext
import reactredux.actions.SetScreen
import reactredux.actions.UpdateContext
import redux.RAction

fun appScreen(state: AppScreen = AppScreen.VALIDATOR, action: RAction): AppScreen =
    when (action) {
        is SetScreen -> {
            action.appScreen
        }
        else -> state
    }