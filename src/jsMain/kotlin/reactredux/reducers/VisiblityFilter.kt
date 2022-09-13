package reactredux.reducers

import reactredux.actions.SetVisibilityFilter
import reactredux.enums.VisibilityFilter
import redux.RAction

fun visibilityFilter(
    state: VisibilityFilter = VisibilityFilter.SHOW_ALL,
    action: RAction
): VisibilityFilter = when (action) {
    is SetVisibilityFilter -> action.filter
    else -> state
}