package ui.components.buttons

import css.component.buttons.GenericButtonStyle
import css.component.buttons.OptionButtonStyle
import css.text.TextStyle
import kotlinx.css.*
import kotlinx.css.properties.border
import kotlinx.html.js.onClickFunction
import react.*
import styled.css
import styled.styledDiv
import styled.styledImg
import styled.styledP

external interface OptionButtonProps : RProps {
    var label: String
    var active: Boolean

    // Callback function when clicked
    var onSelected: () -> Unit
}

/**
 * A generic button with the option to customize, color, label, and optional icon
 */
class OptionButton : RComponent<OptionButtonProps, RState>() {

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

fun RBuilder.optionButton(handler: OptionButtonProps.() -> Unit): ReactElement {
    return child(OptionButton::class) {
        this.attrs(handler)
    }
}