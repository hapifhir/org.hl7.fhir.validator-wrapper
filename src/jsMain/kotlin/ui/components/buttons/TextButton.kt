package ui.components.buttons

import css.const.INACTIVE_GRAY
import css.text.TextStyle
import kotlinx.css.*
import kotlinx.css.properties.border
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.attrs
import styled.StyleSheet
import styled.css
import styled.styledDiv
import styled.styledP

external interface TextButtonProps : RProps {
    var textColor: Color
    var label: String
    var active: Boolean

    // Callback function when clicked
    var onSelected: () -> Unit
}

/**
 * A text only button with the option to customize, color, label, and if it is currently active
 */
class TextButton : RComponent<TextButtonProps, RState>() {
    override fun RBuilder.render() {
        // main button layout
        styledDiv {
            css {
                +TextButtonStyle.button
                border(width = 1.px, style = BorderStyle.solid, color = Color.transparent, borderRadius = 5.px)
                backgroundColor = Color.transparent
                if (props.active) {
                    cursor = Cursor.pointer
                    hover {
                        backgroundColor = props.textColor.changeAlpha(0.1)
                    }
                    active {
                        backgroundColor = props.textColor
                    }
                } else {
                    cursor = Cursor.default
                }
            }
            attrs {
                if (props.active) {
                    onClickFunction = {
                        // on click, we call the function passed in to the props
                        props.onSelected()
                    }
                }
            }
            // button label
            styledP {
                css {
                    +TextStyle.textButtonLabel
                    color = if (props.active) {
                        props.textColor
                    } else {
                        INACTIVE_GRAY
                    }
                }
                +props.label
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.textButton(handler: TextButtonProps.() -> Unit): ReactElement {
    return child(TextButton::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object TextButtonStyle : StyleSheet("TextButtonStyle", isStatic = true) {
    val button by TextButtonStyle.css {
        display = Display.flex
        alignItems = Align.flexStart
        flexDirection = FlexDirection.row
        padding(horizontal = 16.px, vertical = 8.px)
    }
}