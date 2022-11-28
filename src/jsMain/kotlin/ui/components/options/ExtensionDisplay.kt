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

external interface ExtensionDisplayProps : Props {
    var url: String
    var polyglot: Polyglot
    var onDelete: () -> Unit
}

class ExtensionDisplay : RComponent<ExtensionDisplayProps, State>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                +ExtensionDisplayStyle.mainDiv
            }
            styledSpan {
                css {
                    +TextStyle.dropDownLabel
                    +ExtensionDisplayStyle.extensionName
                }
                +props.url
            }
            styledImg {
                css {
                    +ExtensionDisplayStyle.closeButton
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
fun RBuilder.extensionDisplay(handler: ExtensionDisplayProps.() -> Unit) {
    return child(ExtensionDisplay::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object ExtensionDisplayStyle : StyleSheet("ExtensionDisplayStyle", isStatic = true) {
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
