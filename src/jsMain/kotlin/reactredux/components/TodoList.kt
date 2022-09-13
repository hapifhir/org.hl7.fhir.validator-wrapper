package reactredux.components

import react.*
import reactredux.entities.Todo
import react.dom.ul

external interface TodoListProps : Props {
    var todos: Array<Todo>
    var toggleTodo: (Int) -> Unit
    var deleteTodo: (Int) -> Unit
    var updateTodo: (Int, String) -> Unit
}

@JsExport
class TodoList(props: TodoListProps) : RComponent<TodoListProps, State>(props) {
    override fun RBuilder.render() {
        ul {
            props.todos.forEach {
                todo(
                    todo = it,
                    onClick = { props.toggleTodo(it.id) },
                    onDelete = { props.deleteTodo(it.id) },
                    onUpdate = { newText -> props.updateTodo(it.id, newText) }
                )
            }
        }
    }
}