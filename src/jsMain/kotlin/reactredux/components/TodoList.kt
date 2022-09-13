package reactredux.components

import reactredux.entities.Todo
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.ul

external interface TodoListProps : RProps {
    var todos: Array<Todo>
    var toggleTodo: (Int) -> Unit
}

@JsExport
class TodoList(props: TodoListProps) : RComponent<TodoListProps, RState>(props) {
    override fun RBuilder.render() {
        ul {
            props.todos.forEach { todo(it) { props.toggleTodo(it.id) } }
        }
    }
}