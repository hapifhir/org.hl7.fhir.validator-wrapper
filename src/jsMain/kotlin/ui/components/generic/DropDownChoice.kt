package ui.components.generic

import css.component.DropdownChoiceStyle
import css.text.TextStyle
import kotlinx.css.Display
import kotlinx.css.FlexDirection
import kotlinx.css.display
import kotlinx.css.flexDirection
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onMouseOutFunction
import kotlinx.html.js.onMouseOverFunction
import react.*
import styled.css
import styled.styledDiv
import styled.styledSpan

/**
 * Data class for managing list of displayed items. Each entry consists of a label, and selected state.
 */
data class ChoiceSelectableItem(var value: String, var selected: Boolean = false)

external interface DropDownChoiceProps : RProps {
    // Callback for item selected.
    var onSelected: (String, MutableList<ChoiceSelectableItem>) -> Unit

    // List of choices to populate the list with.
    var choices: MutableList<ChoiceSelectableItem>

    // Default label for button, will be replaced with the selected String option, once a selection is made.
    var buttonLabel: String
}

class DropDownChoiceState : RState {
    var dropDownChoiceDisplayed = false
}

/**
 * A dropdown menu, containing a list of choices (Strings). Once a choice is selected, the menu will close.
 * Button label will display the currently selected item.
 */
class DropDownChoice : RComponent<DropDownChoiceProps, DropDownChoiceState>() {

    init {
        state = DropDownChoiceState()
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                +DropdownChoiceStyle.dropDownChoiceContainer
            }
            styledDiv {
                css {
                    display = Display.flex
                    flexDirection = FlexDirection.row
                }
                styledDiv {
                    css {
                        +DropdownChoiceStyle.dropDownChoiceButton
                        +TextStyle.settingButton
                    }
                    attrs {
                        onClickFunction = {
                            setState { dropDownChoiceDisplayed = !dropDownChoiceDisplayed }
                        }
                    }
                    if (props.choices.any { it.selected }) {
                        +props.choices.first { it.selected }.value
                    } else {
                        +props.buttonLabel
                    }
                }
            }
            styledDiv {
                css {
                    +DropdownChoiceStyle.dropDownChoiceContent
                    display = if (state.dropDownChoiceDisplayed) {
                        Display.inlineBlock
                    } else {
                        Display.none
                    }
                }
                attrs {
                    onMouseOverFunction = {
                        setState { dropDownChoiceDisplayed = true }
                    }
                    onMouseOutFunction = {
                        setState { dropDownChoiceDisplayed = false }
                    }
                }
                props.choices.forEach { choice ->
                    styledSpan {
                        attrs {
                            onClickFunction = {
                                onChoiceSelected(choice.value)
                                setState {
                                    setState { dropDownChoiceDisplayed = false }
                                }
                            }
                        }
                        +choice.value
                    }
                }
            }
        }
    }

    /**
     * When a given choice is selected from the list, we need to update the list such only that choice is marked as
     * selected.
     */
    private fun onChoiceSelected(choice: String) {
        setState {
            props.choices.forEach { it.selected = (it.value == choice) }
        }
        props.onSelected(choice, props.choices)
    }
}

/**
 * Convenience method for instantiating the component.
 */
fun RBuilder.dropDownChoice(handler: DropDownChoiceProps.() -> Unit): ReactElement {
    return child(DropDownChoice::class) {
        this.attrs(handler)
    }
}
