package reactredux.reducers

import reactredux.entities.Todo
import reactredux.enums.VisibilityFilter
import redux.RAction

data class State(
    val todos: Array<Todo> = emptyArray(),
    val visibilityFilter: VisibilityFilter = VisibilityFilter.SHOW_ALL
)

fun rootReducer(
    state: State,
    action: Any
) = State(
    todos(state.todos, action.unsafeCast<RAction>()),
    visibilityFilter(state.visibilityFilter, action.unsafeCast<RAction>()),
)