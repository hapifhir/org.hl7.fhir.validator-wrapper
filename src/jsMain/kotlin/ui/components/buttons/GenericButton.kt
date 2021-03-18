package ui.components.buttons

import css.component.buttons.GenericButtonStyle
import css.text.TextStyle
import kotlinx.css.*
import kotlinx.css.properties.border
import kotlinx.html.js.onClickFunction
import react.*
import styled.css
import styled.styledDiv
import styled.styledImg
import styled.styledP

external interface GenericButtonProps : RProps {
    var image: String?
    var borderColor: Color
    var backgroundColor: Color
    var label: String

    // Callback function when clicked
    var onSelected: () -> Unit
}

/**
 * A generic button with the option to customize, color, label, and optional icon
 */
class GenericButton : RComponent<GenericButtonProps, RState>() {

    override fun RBuilder.render() {
        // main button layout
        styledDiv {
            css {
                +GenericButtonStyle.button
                border(width = 1.px, style = BorderStyle.solid, color = props.borderColor, borderRadius = 5.px)
                backgroundColor = props.backgroundColor
                hover {
                    backgroundColor = props.borderColor.changeAlpha(0.1)
                }
                active {
                    backgroundColor = props.borderColor
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
                    +TextStyle.genericButtonLabel
                    if (!props.image.isNullOrEmpty()) {
                        // image has been defined, so we include right padding
                        margin(left = 0.px, top = 0.px, right = 8.px, bottom = 0.px)
                    }
                }
                +props.label
            }

            // button image
            if (!props.image.isNullOrEmpty()) {
                styledImg {
                    css {
                        +GenericButtonStyle.buttonImage
                    }
                    attrs {
                        src = props.image!!
                    }
                }
            }
        }
    }
}

fun RBuilder.genericButton(handler: GenericButtonProps.() -> Unit): ReactElement {
    return child(GenericButton::class) {
        this.attrs(handler)
    }
}