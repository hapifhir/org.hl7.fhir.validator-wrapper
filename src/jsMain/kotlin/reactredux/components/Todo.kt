package reactredux.components

import kotlinx.css.properties.TextDecorationLine
import kotlinx.css.properties.textDecoration
import kotlinx.html.js.onClickFunction
import reactredux.entities.Todo
import react.RBuilder
import styled.css
import styled.styledLi

fun RBuilder.todo(todo: Todo, onClick: () -> Unit) =
    styledLi {
        attrs.onClickFunction = { onClick() }
        css {
            if (todo.completed) textDecoration(TextDecorationLine.lineThrough)
        }
        +todo.text
    }