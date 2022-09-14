package ui.components.buttons

import css.const.BORDER_GRAY
import css.const.HIGHLIGHT_GRAY
import css.const.WHITE
import css.text.TextStyle
import kotlinx.css.*
import kotlinx.css.properties.border
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.attrs
import styled.StyleSheet
import styled.css
import styled.styledDiv
import styled.styledSpan

external interface ToggleButtonProps : Props {
    var optionOneLabel: String
    var optionTwoLabel: String

    // Callback function when option selected
    var onOptionOne: () -> Unit
    var onOptionTwo: () -> Unit
}

class ToggleButtonState : State {
    var optionOneSelected: Boolean = true
}

/**
 * A text only button with the option to customize, color, label, and if it is currently active
 */
class ToggleButton : RComponent<ToggleButtonProps, ToggleButtonState>() {

    init {
        state = ToggleButtonState()
    }

    override fun RBuilder.render() {
        // main button layout
        styledDiv {
            css {
                +ToggleButtonStyle.toggleButtonContainer
            }
            // option one
            styledDiv {
                css {
                    +TextStyle.toggleButtonLabel
                    +ToggleButtonStyle.optionLeft
                    backgroundColor = if (state.optionOneSelected) HIGHLIGHT_GRAY else WHITE
                    active {
                        if (!state.optionOneSelected) backgroundColor = BORDER_GRAY
                    }
                }
                attrs {
                    onClickFunction = {
                        if (!state.optionOneSelected) {
                            setState {
                                optionOneSelected = true
                            }
                            props.onOptionOne()
                        }
                    }
                }
                +props.optionOneLabel
            }
            // option two
            styledSpan {
                css {
                    +TextStyle.toggleButtonLabel
                    +ToggleButtonStyle.optionRight
                    backgroundColor = if (!state.optionOneSelected) HIGHLIGHT_GRAY else WHITE
                    active {
                        if (state.optionOneSelected) backgroundColor = BORDER_GRAY
                    }
                }
                attrs {
                    onClickFunction = {
                        if (state.optionOneSelected) {
                            setState {
                                optionOneSelected = false
                            }
                            props.onOptionTwo()
                        }
                    }
                }
                +props.optionTwoLabel
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.toggleButton(handler: ToggleButtonProps.() -> Unit) {
    return child(ToggleButton::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object ToggleButtonStyle : StyleSheet("ToggleButtonStyle", isStatic = true) {
    val toggleButtonContainer by ToggleButtonStyle.css {
        display = Display.inlineFlex
        flexDirection = FlexDirection.row
    }
    val button by ToggleButtonStyle.css {
        alignItems = Align.center
        justifyContent = JustifyContent.center
        display = Display.flex
        minHeight = 42.px
        minWidth = 96.px
        cursor = Cursor.pointer
    }
    val optionLeft by ToggleButtonStyle.css {
        +button
        border(width = 1.px, color = BORDER_GRAY, style = BorderStyle.solid)
        borderTopLeftRadius = 8.px
        borderBottomLeftRadius = 8.px
    }
    val optionRight by ToggleButtonStyle.css {
        +button
        border(width = 1.px, color = BORDER_GRAY, style = BorderStyle.solid)
        borderTopRightRadius = 8.px
        borderBottomRightRadius = 8.px
    }
}