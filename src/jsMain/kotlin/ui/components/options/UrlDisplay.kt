package ui.components.options

import Polyglot
import css.const.*
import css.text.TextStyle
import kotlinx.css.*
import kotlinx.css.properties.border
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.attrs
import styled.*

external interface UrlDisplayProps : Props {
    var url: String
    var polyglot: Polyglot
    var onDelete: () -> Unit
}

class UrlDisplay : RComponent<UrlDisplayProps, State>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                +UrlDisplayStyle.mainDiv
            }
            styledSpan {
                css {
                    +TextStyle.dropDownLabel
                    +UrlDisplayStyle.extensionName
                }
                +props.url
            }
            styledImg {
                css {
                    +UrlDisplayStyle.closeButton
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
fun RBuilder.urlDisplay(handler: UrlDisplayProps.() -> Unit) {
    return child(UrlDisplay::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object UrlDisplayStyle : StyleSheet("UrlDisplayStyle", isStatic = true) {
    val mainDiv by css {
        display = Display.flex
        flexDirection = FlexDirection.row
        border(width = 1.px, style = BorderStyle.solid, color = BORDER_GRAY)
        margin(right = 16.px, top = 4.px, bottom = 4.px)
        padding(horizontal = 16.px, vertical = 8.px)
        backgroundColor = WHITE
    }
    val extensionName by css {
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
