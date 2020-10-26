package ui.components.generic

import css.component.OptionEntryFieldStyle
import kotlinx.browser.document
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLTextAreaElement
import react.*
import styled.*

external interface OptionEntryFieldProps : RProps {
    /*
     * When an entered value is submitted, this method is called. The parent component must return a true or false
     * value, indicating if the submitted option was a valid one. A message with details is returned.
     */
    var submitEntry: (String) -> Pair<Boolean, String>

    // Default option value
    var defaultValue: String
}

class OptionEntryFieldState : RState {
    // There may be a delay in submitting the entered option for validation, and returning a response.
    var validatingChoice = false

    // If currently displaying an error message to the user, this flag will be set to true.
    var displayingErrorMessage = false

    // True if successfully validated
    var currentEntryValidated = true

    // Current error message
    var errorMessage: String = ""
}

/**
 * A text entry field, where the user can enter an option which is submitted to the parent component for validation or
 * processing. Includes a field for displaying possible errors.
 */
class OptionEntryField : RComponent<OptionEntryFieldProps, OptionEntryFieldState>() {

    init {
        state = OptionEntryFieldState()
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                +OptionEntryFieldStyle.mainDiv
            }
            styledDiv {
                css {
                    +OptionEntryFieldStyle.textAreaAndButtonDiv
                }
                styledTextArea {
                    css {
                        +OptionEntryFieldStyle.textArea
                    }
                    attrs {
                        id = "OptionEntryFieldTextArea"
                    }
                    +props.defaultValue
                }
                styledDiv {
                    css {
                        +OptionEntryFieldStyle.buttonDiv
                    }
                    if (state.validatingChoice) {
                        styledDiv {
                            css {
                                +OptionEntryFieldStyle.loadingIndicator
                            }
                        }
                    } else {
                        styledImg {
                            css {
                                +OptionEntryFieldStyle.submitButton
                            }
                            attrs {
                                src = "images/validate.svg"
                                onClickFunction = {
                                    setState {
                                        validatingChoice = true
                                    }
                                    val field = document.getElementById("OptionEntryFieldTextArea") as HTMLTextAreaElement
                                    validateOptionEntry(field.value)
                                    setState {
                                        validatingChoice = false
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (state.displayingErrorMessage) {
                styledDiv {
                    css {
                        +OptionEntryFieldStyle.errorMessage
                    }
                    +state.errorMessage
                }
            }
        }
    }

    /**
     * Validate the entered value, and set component flags accordingly to update the UI.
     */
    private fun validateOptionEntry(entry: String) {
        val (successful, message) = props.submitEntry(entry)
        if (successful) {
            setState {
                displayingErrorMessage = false
                currentEntryValidated = true
            }
        } else {
            setState {
                displayingErrorMessage = true
                currentEntryValidated = false
                errorMessage = message
            }
        }
    }
}

/**
 * Convenience method for instantiating the component.
 */
fun RBuilder.optionEntryField(handler: OptionEntryFieldProps.() -> Unit): ReactElement {
    return child(OptionEntryField::class) {
        this.attrs(handler)
    }
}
