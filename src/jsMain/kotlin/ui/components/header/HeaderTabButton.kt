package ui.components.header

import css.component.header.HeaderButtonIndicatorStyle
import css.component.header.HeaderStyle
import css.text.TextStyle
import kotlinx.html.js.onClickFunction
import react.*
import styled.css
import styled.styledSpan

external interface HeaderTabButtonProps : RProps {
    var selected: Boolean
    var label: String
    var onSelected: (String) -> Unit
}

class HeaderTabButtonState : RState {}

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

fun RBuilder.headerTabButton(handler: HeaderTabButtonProps.() -> Unit): ReactElement {
    return child(HeaderTabButton::class) {
        this.attrs(handler)
    }
}