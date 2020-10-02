package ui.components.generic

import css.component.DropdownChoiceStyle
import css.text.TextStyle
import kotlinx.browser.document
import kotlinx.css.Display
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLDivElement
import react.*
import styled.css
import styled.styledButton
import styled.styledDiv
import styled.styledSpan

external interface DropDownChoiceProps : RProps {
    // Callback for item selected.
    var onSelected: (String) -> Unit

    // List of choices to populate the list with.
    var choices: List<String>

    // Default label for button, will be replaced with the selected String option, once a selection is made.
    var defaultButtonLabel: String
}

class DropDownChoiceState : RState {
    lateinit var currentButtonLabel: String
}

/**
 * A dropdown menu, containing a list of choices (Strings)
 */
class DropDownChoice : RComponent<DropDownChoiceProps, DropDownChoiceState>() {

    init {
        state = DropDownChoiceState()
        setState {
            currentButtonLabel = props.defaultButtonLabel
        }
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                +DropdownChoiceStyle.dropDownChoiceContainer
            }
            styledButton {
                css {
                    +DropdownChoiceStyle.dropDownChoiceButton
                    +TextStyle.settingButton
                }
                attrs {
                    onClickFunction = {
                        var div = document.getElementById("myDropdown") as HTMLDivElement
                        div.style.display = Display.block.toString()
                    }
                }
                +props.defaultButtonLabel
            }
            styledDiv {
                css {
                    +DropdownChoiceStyle.dropDownChoiceContent
                }
                attrs {
                    id = "myDropdown"
                }
                props.choices.forEach { choice ->
                    styledSpan {
                        attrs {
                            onClickFunction = {
                                props.onSelected(choice)
                                var div = document.getElementById("myDropdown") as HTMLDivElement
                                div.style.display = Display.none.toString()
                                setState {
                                    props.defaultButtonLabel = choice
                                }
                            }
                        }
                        +choice
                    }
                }
            }
        }
    }
}

fun RBuilder.dropDownChoice(handler: DropDownChoiceProps.() -> Unit): ReactElement {
    return child(DropDownChoice::class) {
        this.attrs(handler)
    }
}
