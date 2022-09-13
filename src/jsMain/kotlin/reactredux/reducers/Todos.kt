package reactredux.reducers

import reactredux.actions.AddTodo
import reactredux.actions.ToggleTodo
import reactredux.entities.Todo
import redux.RAction

fun todos(state: Array<Todo> = emptyArray(), action: RAction): Array<Todo> = when (action) {
    is AddTodo -> state + Todo(action.id, action.text, false)
    is ToggleTodo -> state.map {
        if (it.id == action.id) {
            it.copy(completed = !it.completed)
        } else {
            it
        }
    }.toTypedArray()
    else -> state
}