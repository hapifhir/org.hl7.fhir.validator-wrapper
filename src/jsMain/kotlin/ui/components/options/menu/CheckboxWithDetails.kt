package ui.components.options.menu

import css.text.TextStyle
import kotlinx.css.*
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.attrs
import styled.*

const val CHECKBOX_CHANGE = "change"

external interface CheckboxWithDetailsProps : RProps {
    var name: String
    var description: String
    var selected: Boolean

    var onChange: (Boolean) -> Unit
}

class CheckboxWithDetailsState : RState {
    var currentlyExpanded: Boolean = false
}

class CheckboxWithDetails : RComponent<CheckboxWithDetailsProps, CheckboxWithDetailsState>() {

    init {
        state = CheckboxWithDetailsState()
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                +CheckboxStyle.checkboxTitle
            }
            styledInput(type = InputType.checkBox) {
                css {
                    alignSelf = Align.center
                }
                attrs {
                    defaultChecked = props.selected
                    onChangeFunction = { event ->
                        if (event.type == CHECKBOX_CHANGE) {
                            props.onChange((event.target as HTMLInputElement).checked)
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
                            currentlyExpanded = !currentlyExpanded
                        }
                    }
                }
                styledSpan {
                    css {
                        alignSelf = Align.center
                        +TextStyle.optionName
                    }
                    +props.name
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
                            src = if (state.currentlyExpanded) {
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
                display = if (state.currentlyExpanded) {
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
                    +TextStyle.optionsDetailText
                }
                +props.description
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.checkboxWithDetails(handler: CheckboxWithDetailsProps.() -> Unit): ReactElement {
    return child(CheckboxWithDetails::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object CheckboxStyle : StyleSheet("CheckboxStyle", isStatic = true) {
    val propertiesDetails by css {
        overflow = Overflow.hidden
    }
    val dropdownButtonContainer by css {
        display = Display.flex
        flex(flexGrow = 1.0)
        flexDirection = FlexDirection.row
        justifyContent = JustifyContent.flexEnd
    }
    val dropdownButton by css {
        width = 24.px
        height = 24.px
        alignSelf = Align.center
    }
    val checkboxTitleBar by css {
        display = Display.flex
        flex(flexGrow = 1.0)
        flexDirection = FlexDirection.row
        alignSelf = Align.center
        padding(horizontal = 16.px, vertical = 8.px)
    }
    val checkboxTitle by css {
        flexGrow = 1.0
        width = 100.pct
        display = Display.flex
        flexDirection = FlexDirection.row
        justifyContent = JustifyContent.flexStart
    }
}