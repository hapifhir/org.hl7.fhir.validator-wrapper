package ui.components.header

import css.const.HL7_RED
import css.text.TextStyle
import kotlinx.css.*
import kotlinx.css.properties.*
import kotlinx.html.js.onClickFunction
import react.*
import styled.StyleSheet
import styled.css
import styled.styledSpan

external interface HeaderTabButtonProps : RProps {
    var selected: Boolean
    var label: String
    var onSelected: (String) -> Unit
}

class HeaderTabButtonState : RState

class HeaderTabButton : RComponent<HeaderTabButtonProps, HeaderTabButtonState>() {

    init {
        state = HeaderTabButtonState()
    }

    override fun RBuilder.render() {
        styledSpan {
            css {
                if (props.selected) {
                    +HeaderButtonIndicatorStyle.headerButtonIndicatorSelected
                } else {
                    +HeaderButtonIndicatorStyle.headerButtonIndicator
                }
                +HeaderStyle.menuEntries
                +TextStyle.headerButtonLabel
            }
            attrs {
                onClickFunction = {
                    props.onSelected(props.label)
                }
            }
            +props.label
        }
    }
}

/**
 * Convenience method for instantiating the component.
 */
fun RBuilder.headerTabButton(handler: HeaderTabButtonProps.() -> Unit): ReactElement {
    return child(HeaderTabButton::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object HeaderButtonIndicatorStyle : StyleSheet("HeaderButtonIndicator", isStatic = true) {

    val headerButtonIndicator by css {
        cursor = Cursor.pointer
        hover {
            color = HL7_RED
            after {
                transform {
                    scaleX(1)
                }
            }
        }
        after {
            display = Display.block
            content = QuotedString("")
            border(width = 1.px, style = BorderStyle.solid, color = HL7_RED)
            transform {
                scaleX(0)
            }
            transition(duration = 250.ms, timing = Timing.easeInOut, delay = 0.ms)
        }
    }
    val headerButtonIndicatorSelected by css {
        cursor = Cursor.pointer
        after {
            display = Display.block
            content = QuotedString("")
            border(width = 1.px, style = BorderStyle.solid, color = HL7_RED)
        }
    }
}