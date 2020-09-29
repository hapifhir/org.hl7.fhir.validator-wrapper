package ui.components

import css.widget.CheckboxStyle
import css.text.TextStyle
import kotlinx.css.*
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import react.*
import styled.*

const val CHECKBOX_CHANGE = "change"

external interface CheckboxInputProps : RProps {
    var onChange: (Boolean) -> Unit
    var settingName: String
    var settingDescription: String
}

class CheckboxInputState : RState {
    var detailsOpen: Boolean = false
}

class CheckboxInput : RComponent<CheckboxInputProps, CheckboxInputState>() {

    init {
        state = CheckboxInputState()
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                +CheckboxStyle.checkboxTitle
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
            styledDiv {
                css {
                    +CheckboxStyle.checkboxTitleBar
                }
                attrs {
                    onClickFunction = {
                        setState {
                            detailsOpen = !detailsOpen
                        }
                    }
                }
                styledSpan {
                    css {
                        alignSelf = Align.center
                        +TextStyle.settingName
                    }
                    +props.settingName
                }
                styledDiv {
                    css {
                        +CheckboxStyle.dropdownButtonContainer
                    }
                    styledImg {
                        css {
                            +CheckboxStyle.dropdownButton
                        }
                        attrs {
                            src = if (state.detailsOpen) {
                                "images/arrow_up.svg"
                            } else {
                                "images/arrow_down.svg"
                            }
                        }
                    }
                }
            }
        }
        styledDiv {
            css {
                +CheckboxStyle.propertiesDetails
                display = if (state.detailsOpen) {
                    Display.flex
                } else {
                    Display.none
                }
            }
            attrs {
                id = "setting_info_drawer"
            }
            styledSpan {
                css {
                    +TextStyle.code
                    padding(1.rem)
                }
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
