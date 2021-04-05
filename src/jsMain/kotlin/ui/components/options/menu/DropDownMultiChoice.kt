package ui.components.options.menu

import css.const.*
import css.text.TextStyle
import kotlinx.css.*
import kotlinx.css.properties.border
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onMouseOutFunction
import kotlinx.html.js.onMouseOverFunction
import react.*
import styled.*

external interface DropDownMultiChoiceProps : RProps {
    /*
     * Callback for when item in list is selected. Returns newly selected item, as well as
     * list of all selected options currently
     */
    var onSelected: (String) -> Unit

    // Initial list of choices to populate the list with. Selected labels are paired with true.
    var choices: MutableList<Pair<String, Boolean>>

    // Default label for button, will be replaced with the selected String option, once a selection is made.
    var buttonLabel: String

    // Indicates if this is a dropdown supporting multiple selections, or a single selection
    var multichoice: Boolean
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
                    +DropDownMultiChoiceStyle.buttonLayout
                }
                attrs {
                    onClickFunction = {
                        setState {
                            dropDownMultiChoiceDisplayed = !dropDownMultiChoiceDisplayed
                        }
                    }
                }
                styledDiv {
                    css {
                        +DropDownMultiChoiceStyle.dropDownLabel
                        +TextStyle.dropDownLabel
                    }
                    if(!props.multichoice && props.choices.any{ it.second } ) {
                        +props.choices.first { it.second }.first
                    } else {
                        +props.buttonLabel
                    }
                }
                styledImg {
                    css {
                        +DropDownMultiChoiceStyle.dropDownArrowIcon
                    }
                    attrs {
                        src = if (state.dropDownMultiChoiceDisplayed) {
                            "images/arrow_up.svg"
                        } else {
                            "images/arrow_down.svg"
                        }
                    }
                }
            }
            styledDiv {
                css {
                    if (state.dropDownMultiChoiceDisplayed) {
                        +DropDownMultiChoiceStyle.dropDownActive
                    } else {
                        +DropDownMultiChoiceStyle.dropDownHidden
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
                val entryList = props.choices
                    .filterNot { it.second }
                    .map { it.first }
                    .onEach { println("Displaying :: ${it}") }
                    .iterator()
                while (entryList.hasNext()) {
                    val choice = entryList.next()
                    styledSpan {
                        css {
                            +DropDownMultiChoiceStyle.dropdownChoice
                        }
                        attrs {
                            onClickFunction = {
                                onChoiceSelected(choice)
                            }
                        }
                        +choice
                    }
                    if (entryList.hasNext()) {
                        styledDiv {
                            css {
                                +DropDownMultiChoiceStyle.dropDownDivider
                            }
                        }
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
            props.onSelected(choice)
        }
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

/**
 * CSS
 */
object DropDownMultiChoiceStyle : StyleSheet("DropDownMultiChoice", isStatic = true) {
    val mainDiv by css {
        cursor = Cursor.pointer
        position = Position.relative
        display = Display.inlineBlock
    }
    val buttonLayout by css {
        display = Display.flex
        flexDirection = FlexDirection.row
        width = LinearDimension.fitContent
        border(width = 1.px, color = BORDER_GRAY, style = BorderStyle.solid)
        padding(horizontal = 16.px, vertical = 8.px)
    }
    val dropDownLabel by css {
        backgroundColor = WHITE
        flexGrow = 1.0
        paddingRight = 48.px
    }
    val dropDownArrowIcon by css {
        width = 24.px
        height = 24.px
        alignSelf = Align.center
    }
    val dropDownContent by css {
        position = Position.absolute
        backgroundColor = WHITE
        overflowY = Overflow.scroll
        overflowX = Overflow.hidden
        border(width = 1.px, color = BORDER_GRAY, style = BorderStyle.solid)
        minWidth = 148.px
        zIndex = 1
    }
    val dropDownActive by css {
        +dropDownContent
        display = Display.flex
        flexDirection = FlexDirection.column
    }
    val dropDownHidden by css {
        +dropDownContent
        display = Display.none
    }
    val dropdownChoice by css {
        padding(vertical = 12.px, horizontal = 16.px)
        fontFamily = TextStyle.FONT_FAMILY_MAIN
        fontSize = 12.pt
        fontWeight = FontWeight.w200
        color = TEXT_BLACK
        display = Display.block
        width = 100.pct
        hover {
            backgroundColor = INACTIVE_GRAY
        }
        active {
            backgroundColor = HIGHLIGHT_GRAY
        }
    }
    val dropDownDivider by css {
        width = 100.pct
        height = 1.px
        backgroundColor = HIGHLIGHT_GRAY
    }
}
