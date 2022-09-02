package ui.components.header

import css.const.HL7_RED
import css.text.TextStyle
import kotlinx.css.*
import kotlinx.css.properties.*
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.attrs
import styled.StyleSheet
import styled.css
import styled.styledDiv
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
        styledDiv {
            css {
                +HeaderButtonIndicatorStyle.headerButtonMainContainer
            }
            attrs {
                onClickFunction = {
                    props.onSelected(props.label)
                }
            }
            styledDiv {
                css {
                    +HeaderButtonIndicatorStyle.headerButtonTextContainer
                }
                styledSpan {
                    css {
                        +TextStyle.headerButtonLabel
                    }
                    +props.label
                }
            }
            styledDiv {
                css {
                    +HeaderButtonIndicatorStyle.buttonIndicator
                    if (!props.selected) {
                        +HeaderButtonIndicatorStyle.animatedButtonIndicator
                    }
                }
            }
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
    val headerButtonMainContainer by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        cursor = Cursor.pointer
        padding(horizontal = 16.px)
    }
    val headerButtonTextContainer by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        justifyContent = JustifyContent.center
        flexGrow = 1.0
        marginTop = 16.px
    }
    val buttonIndicator by css {
        height = 2.px
        backgroundColor = HL7_RED
    }
    val animatedButtonIndicator by css {
        transform {
            scaleX(0)
        }
        transition(duration = 250.ms, timing = Timing.easeInOut, delay = 0.ms)
        ancestorHover(".${HeaderButtonIndicatorStyle.name}-${HeaderButtonIndicatorStyle::headerButtonMainContainer.name}") {
            transform {
                scaleX(1)
            }
        }
    }
}