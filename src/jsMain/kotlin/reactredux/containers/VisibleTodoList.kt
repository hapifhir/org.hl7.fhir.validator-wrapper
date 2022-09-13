package reactredux.containers

import reactredux.actions.DeleteTodo
import reactredux.actions.EditTodo
import reactredux.actions.ToggleTodo
import reactredux.components.TodoList
import reactredux.components.TodoListProps
import reactredux.entities.Todo
import reactredux.enums.VisibilityFilter
import reactredux.reducers.State
import react.ComponentClass
import react.Props
import react.invoke
import react.redux.rConnect
import redux.RAction
import redux.WrapperAction

private fun getVisibleTodos(todos: Array<Todo>, filter: VisibilityFilter): Array<Todo> = when (filter) {
    VisibilityFilter.SHOW_ALL -> todos
    VisibilityFilter.SHOW_ACTIVE -> todos.filter { !it.completed }.toTypedArray()
    VisibilityFilter.SHOW_COMPLETED -> todos.filter { it.completed }.toTypedArray()
}

private external interface TodoListStateProps : Props {
    var todos: Array<Todo>
}

private external interface TodoListDispatchProps : Props {
    var toggleTodo: (Int) -> Unit
    var deleteTodo: (Int) -> Unit
    var updateTodo: (Int, String) -> Unit
}

val visibleTodoList: ComponentClass<Props> =
    rConnect<State, RAction, WrapperAction, Props, TodoListStateProps, TodoListDispatchProps, TodoListProps>(
        { state, _ ->
            todos = getVisibleTodos(state.todos, state.visibilityFilter)
        },
        { dispatch, _ ->
            toggleTodo = { dispatch(ToggleTodo(it)) }
            deleteTodo = { dispatch(DeleteTodo(it)) }
            updateTodo = { id, newText -> dispatch(EditTodo(id, newText)) }
        }
    )(TodoList::class.js.unsafeCast<ComponentClass<TodoListProps>>())