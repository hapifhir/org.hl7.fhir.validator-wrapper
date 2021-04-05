package ui.components.options

import css.const.BORDER_GRAY
import css.const.HIGHLIGHT_GRAY
import css.const.INACTIVE_GRAY
import css.const.WHITE
import css.text.TextStyle
import kotlinx.css.*
import kotlinx.css.properties.border
import kotlinx.html.js.onClickFunction
import react.*
import styled.*

external interface IgUrlDisplayProps : RProps {
    var igUrl: String
    var onDelete: () -> Unit
}

class IgUrlDisplay : RComponent<IgUrlDisplayProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                +IgUrlDisplayStyle.mainDiv
            }
            styledSpan {
                css {
                    +TextStyle.dropDownLabel
                    +IgUrlDisplayStyle.igName
                }
                +props.igUrl
            }
            styledImg {
                css {
                    +IgUrlDisplayStyle.closeButton
                }
                attrs {
                    src = "images/close_black.png"
                    onClickFunction = {
                        props.onDelete()
                    }
                }
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.igUrlDisplay(handler: IgUrlDisplayProps.() -> Unit): ReactElement {
    return child(IgUrlDisplay::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object IgUrlDisplayStyle : StyleSheet("IgUrlDisplayStyle", isStatic = true) {
    val mainDiv by css {
        display = Display.flex
        flexDirection = FlexDirection.row
        border(width = 1.px, style = BorderStyle.solid, color = BORDER_GRAY)
        margin(right = 16.px, top = 4.px, bottom = 4.px)
        padding(horizontal = 16.px, vertical = 8.px)
        backgroundColor = WHITE
    }
    val igName by css {
        padding(right = 16.px)
    }
    val closeButton by css {
        width = 16.px
        height = 16.px
        alignSelf = Align.center
        borderRadius = 50.pct
        hover {
            backgroundColor = HIGHLIGHT_GRAY
        }
        active {
            backgroundColor = INACTIVE_GRAY
        }
    }
}
