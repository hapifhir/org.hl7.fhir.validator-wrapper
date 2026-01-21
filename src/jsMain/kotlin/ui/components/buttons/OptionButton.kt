package ui.components.buttons

import css.const.BORDER_GRAY
import css.const.INACTIVE_GRAY
import css.const.WHITE
import css.text.TextStyle
import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import kotlinx.css.properties.border
import react.*
import react.dom.attrs
import styled.StyleSheet
import styled.css
import styled.styledDiv
import styled.styledP

external interface OptionButtonProps : Props {
    var label: String
    var active: Boolean

    // Callback function when clicked
    var onSelected: () -> Unit
}

/**
 * A generic button with the option to customize, color, label, and optional icon
 */
class OptionButton : RComponent<OptionButtonProps, State>() {

    override fun RBuilder.render() {
        // main button layout
        styledDiv {
            css {
                if (props.active) {
                    +OptionButtonStyle.buttonActive
                } else {
                    +OptionButtonStyle.buttonInactive
                }
            }
            attrs {
                onClickFunction = {
                    // on click, we call the function passed in to the props
                    props.onSelected()
                }
            }
            // button label
            styledP {
                css {
                    +TextStyle.optionButtonLabel
                }
                +props.label
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.optionButton(handler: OptionButtonProps.() -> Unit) {
    return child(OptionButton::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object OptionButtonStyle : StyleSheet("OptionButtonStyle", isStatic = true) {
    val button by OptionButtonStyle.css {
        cursor = Cursor.pointer
        display = Display.inlineFlex
        minHeight = 24.px
        minWidth = 160.px
        alignSelf = Align.center
        justifyContent = JustifyContent.center
        alignItems = Align.center
        border(width = 1.px, style = BorderStyle.solid, color = BORDER_GRAY)
        borderRadius = 999.px
    }
    val buttonInactive by OptionButtonStyle.css {
        +button
        backgroundColor = INACTIVE_GRAY
        hover {
            backgroundColor = WHITE
        }
        active {
            backgroundColor = BORDER_GRAY
        }
    }
    val buttonActive by OptionButtonStyle.css {
        +button
        backgroundColor = WHITE
        hover {
            backgroundColor = INACTIVE_GRAY
        }
        active {
            backgroundColor = BORDER_GRAY
        }
    }
}
