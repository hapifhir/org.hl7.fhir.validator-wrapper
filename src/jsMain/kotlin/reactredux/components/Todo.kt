package reactredux.components

import kotlinx.css.properties.TextDecorationLine
import kotlinx.css.properties.textDecoration
import kotlinx.html.js.onClickFunction
import reactredux.entities.Todo
import org.w3c.dom.HTMLInputElement
import react.*
import ringui.*
import styled.css
import styled.styledP

external interface TodoProps : Props {
    var todo: Todo
    var onClick: () -> Unit
    var onDelete: () -> Unit
    var onUpdate: (String) -> Unit
}

private val TodoItem = fc<TodoProps> { props ->
    val (isEdit, setEdit) = useState(false)
    val (editableValue, setEditableValue) = useState(props.todo.text)

    Row {
        attrs.baseline = RowPosition.xs
        attrs.between = RowPosition.xs
        Col {
            if (!isEdit) {
                styledP {
                    css {
                        if (props.todo.completed) textDecoration(TextDecorationLine.lineThrough)
                    }
                    attrs.onClickFunction = { props.onClick() }
                    +props.todo.text
                }
            } else {
                Input {
                    attrs {
                        value = editableValue
                        onChange = { event ->
                            val target = event.target as HTMLInputElement
                            setEditableValue(target.value)
                        }
                    }
                }
            }
        }
        Col {
            ButtonGroup {
                if (isEdit) {
                    Button {
                        attrs.onMouseDown = {
                            setEditableValue(props.todo.text)
                            setEdit(false)
                        }
                        +"Cancel"
                    }
                    Button {
                        attrs {
                            onMouseDown = {
                                props.onUpdate(editableValue)
                                setEdit(false)
                            }
                            disabled = editableValue.isBlank()
                        }
                        +"Save"
                    }
                }
                Button {
                    attrs.onMouseDown = { props.onDelete() }
                    +"Delete"
                }
                if (!isEdit) {
                    Button {
                        attrs.onMouseDown = { setEdit(true) }
                        +"Edit"
                    }
                }
            }
        }
    }
}

fun RBuilder.todo(todo: Todo, onClick: () -> Unit, onDelete: () -> Unit, onUpdate: (String) -> Unit) {
    TodoItem {
        attrs {
            this.todo = todo
            this.onClick = onClick
            this.onDelete = onDelete
            this.onUpdate = onUpdate
        }
    }
}
