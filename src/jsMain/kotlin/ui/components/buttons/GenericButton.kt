package ui.components.buttons

import css.const.TEXT_BLACK
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
                display = Display.inlineFlex
                flexDirection = FlexDirection.row
                minHeight = 32.px
                padding(horizontal = 16.px, vertical = 8.px)
                border(width = 1.px, style = BorderStyle.solid, color = props.borderColor, borderRadius = 5.px)
                backgroundColor = props.backgroundColor
                active {
                    backgroundColor = props.borderColor
                }
                hover {
                    // backgroundColor = Color(props.borderColor and 0x22FFFFFF)
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
                    fontFamily = TextStyle.FONT_FAMILY_MAIN
                    fontSize = 12.pt
                    fontWeight = FontWeight.w400
                    color = TEXT_BLACK
                    alignSelf = Align.center
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
                        height = 16.px
                        width = 16.px
                        alignSelf = Align.center
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