package ui.components.generic

import css.component.DropDownMultiChoiceStyle
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
data class MultiChoiceSelectableItem(var value: String, var selected: Boolean = false)

external interface DropDownMultiChoiceProps : RProps {
    /*
     * Callback for when item in list is selected. Returns newly selected item, as well as
     * list of all selected options currently
     */
    var onSelected: (String, MutableList<MultiChoiceSelectableItem>) -> Unit

    // Initial list of choices to populate the list with.
    var choices: MutableList<MultiChoiceSelectableItem>

    // Default label for button, will be replaced with the selected String option, once a selection is made.
    var buttonLabel: String
}

class DropDownMultiChoiceState : RState {
    var dropDownMultiChoiceDisplayed = false
}

/**
 * A dropdown menu, containing a list of choices (Strings). Multiple choices can be selected at any time. Once an item
 * is selected, it is no longer displayed in the list of available options. Drop down menu is open/closed by clicking
 * on the button. Additionally, the menu will close automatically on mouse out event from the list of options.
 */
class DropDownMultiChoice : RComponent<DropDownMultiChoiceProps, DropDownMultiChoiceState>() {

    init {
        state = DropDownMultiChoiceState()
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                +DropDownMultiChoiceStyle.mainDiv
            }
            styledDiv {
                css {
                    display = Display.flex
                    flexDirection = FlexDirection.row
                }
                styledDiv {
                    css {
                        +DropDownMultiChoiceStyle.dropDownMultiChoiceButton
                        +TextStyle.settingButton
                    }
                    attrs {
                        onClickFunction = {
                            setState {
                                dropDownMultiChoiceDisplayed = !dropDownMultiChoiceDisplayed
                            }
                        }
                    }
                    +props.buttonLabel
                }
            }
            styledDiv {
                css {
                    +DropDownMultiChoiceStyle.dropDownMultiChoiceContent
                    display = if (state.dropDownMultiChoiceDisplayed) {
                        Display.inlineBlock
                    } else {
                        Display.none
                    }
                }
                attrs {
                    onMouseOverFunction = {
                        setState { dropDownMultiChoiceDisplayed = true }
                    }
                    onMouseOutFunction = {
                        setState { dropDownMultiChoiceDisplayed = false }
                    }
                }
                props.choices
                    .filterNot { it.selected }
                    .map { it.value }
                    .forEach { choice ->
                        styledSpan {
                            attrs {
                                onClickFunction = {
                                    onChoiceSelected(choice)
                                }
                            }
                            +choice
                        }
                    }
            }
        }
    }

    /**
     * When a given choice is selected from the list, we need to update the list such that the choice is no longer
     * displayed as an available option.
     */
    private fun onChoiceSelected(choice: String) {
        setState {
            props.choices.first { it.value == choice }.selected = true
        }
        props.onSelected(choice, props.choices)
    }
}

/**
 * Convenience method for instantiating the component.
 */
fun RBuilder.dropDownMultiChoice(handler: DropDownMultiChoiceProps.() -> Unit): ReactElement {
    return child(DropDownMultiChoice::class) {
        this.attrs(handler)
    }
}
