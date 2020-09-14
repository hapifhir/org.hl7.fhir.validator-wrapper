package ui.components

import kotlinx.css.*
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener
import react.*
import react.dom.input
import react.dom.span
import styled.css
import styled.styledDiv
import styled.styledInput
import styled.styledSpan

const val CHECKBOX_CHANGE = "change"

external interface CheckboxInputProps : RProps {
    var onChange: (Boolean) -> Unit
    var settingName: String
    var settingDescription: String
}

class CheckboxInput : RComponent<CheckboxInputProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                display = Display.flex
                flexDirection = FlexDirection.row
                alignSelf = Align.center
            }
            styledInput (type = InputType.checkBox) {
                css {
                    alignSelf = Align.center
                }
                attrs {
                    onChangeFunction = { event ->
                        if (event.type == CHECKBOX_CHANGE) {
                            var inputElement = event.target as HTMLInputElement
                            props.onChange(inputElement.checked)
                        }
                    }
                }
            }
        }
        styledDiv {
            css {
                display = Display.flex
                flexDirection = FlexDirection.column
                alignSelf = Align.center
                padding(1.rem)
            }
            styledSpan {
                +props.settingName
            }
            styledSpan {
                +props.settingDescription
            }
        }

    }
}

fun RBuilder.checkboxInput(handler: CheckboxInputProps.() -> Unit): ReactElement {
    return child(CheckboxInput::class) {
        this.attrs(handler)
    }
}
