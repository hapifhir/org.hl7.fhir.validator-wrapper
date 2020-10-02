package ui.components

import css.const.GRAY_200
import css.const.GRAY_700
import css.const.ICON_SMALL_DIM
import css.text.TextStyle
import kotlinx.css.*
import kotlinx.css.properties.border
import kotlinx.html.js.onClickFunction
import react.*
import styled.css
import styled.styledDiv
import styled.styledImg
import styled.styledP

external interface IgUrlDisplayProps : RProps {
    var onDelete: () -> Unit
    var igUrl: String
}

class IgUrlDisplay : RComponent<IgUrlDisplayProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                display = Display.flex
                flexDirection = FlexDirection.row
                border(width = 2.px, style = BorderStyle.solid, color = GRAY_700)
                margin(right = 0.5.rem, bottom = 0.25.rem)
                hover {
                    backgroundColor = GRAY_200
                }
            }
            styledP {
                css {
                    +TextStyle.codeDark
                    padding(horizontal = 0.5.rem)
                }
                +props.igUrl
            }
            styledImg {
                css {
                    width = ICON_SMALL_DIM
                    height = ICON_SMALL_DIM
                    alignSelf = Align.center
                    padding(horizontal = 0.5.rem)
                }
                attrs {
                    src = "images/close.svg"
                    onClickFunction = {
                        println("on close called")
                        props.onDelete()
                    }
                }
            }
        }
    }
}

fun RBuilder.igUrlDisplay(handler: IgUrlDisplayProps.() -> Unit): ReactElement {
    return child(IgUrlDisplay::class) {
        this.attrs(handler)
    }
}
