package ui.components

import api.sendValidationRequest
import constants.FhirFormat
import css.component.FileUploadStyle
import css.component.ResourceEntryStyle
import css.widget.FABStyle
import css.widget.Spinner
import kotlinx.browser.document
import kotlinx.coroutines.launch
import kotlinx.css.JustifyContent
import kotlinx.css.justifyContent
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onInputFunction
import mainScope
import model.CliContext
import model.ValidationOutcome
import org.w3c.dom.HTMLTextAreaElement
import react.*
import react.dom.value
import styled.*
import ui.components.generic.fileIssueListDisplayComponent
import utils.assembleRequest

external interface ResourceEntryFieldProps : RProps {
    var cliContext: CliContext
    var validationOutcome: ValidationOutcome
    var addManuallyEnteredFileValidationOutcome: (ValidationOutcome) -> Unit
}

class ResourceEntryFieldState : RState {
    var validating: Boolean = false
    var codeDisplay: Boolean = true
}

const val INPUT_TEXT_ID = "inputTextArea"

class ResourceEntryFieldComponent : RComponent<ResourceEntryFieldProps, ResourceEntryFieldState>() {

    init {
        state = ResourceEntryFieldState()
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                +ResourceEntryStyle.entryContainer
            }
            validationResultDisplayMenuComponent {
                closeButton = false
                listDisplay = {
                    setState {
                        codeDisplay = false
                    }
                }
                codeDisplay = {
                    setState {
                        codeDisplay = true
                    }
                }
            }

            if (state.codeDisplay) {
                styledTextArea {
                    css {
                        +ResourceEntryStyle.entryField
                    }
                    attrs {
                        id = INPUT_TEXT_ID
                        placeholder = "Enter Resource Manually"
                        onInputFunction = {
                            props.validationOutcome.getFileInfo().setFileContent(this.value)
                        }
                        /*
                         * If we have a previously entered manual entry, and the user has not already entered a new
                         * value, populate the field with our stored value.
                         */
                        if (this.value.isEmpty() && props.validationOutcome.getFileInfo().getFileContent().isNotEmpty()) {
                            this.value = props.validationOutcome.getFileInfo().getFileContent()
                        }
                    }

                }
            } else {
                fileIssueListDisplayComponent {
                    validationOutcome = props.validationOutcome
                }
            }
            styledDiv {
                css {
                    +FABStyle.endButtonContainer
                }

                if (state.validating) {
                    styledSpan {
                        css {
                            +Spinner.loadingIconLight
                            justifyContent = JustifyContent.center
                        }
                    }
                } else {
                    styledDiv {
                        css {
                            +FileUploadStyle.buttonContainer
                        }
                        styledDiv {
                            css {
                                +FABStyle.fab
                            }
                            styledImg {
                                css {}
                                attrs {
                                    src = "images/validate.svg"
                                }
                            }
                            attrs {
                                onClickFunction = {
                                    val field = document.getElementById(INPUT_TEXT_ID) as HTMLTextAreaElement
                                    mainScope.launch {
                                        setState {
                                            validating = true
                                        }
                                        val returnedOutcome = sendValidationRequest(
                                            assembleRequest(
                                                cliContext = props.cliContext,
                                                fileName = "testfile.json",
                                                fileContent = field.value,
                                                fileType = FhirFormat.JSON
                                            )
                                        )
                                        println("File validated\n"
                                                + "filename -> " + returnedOutcome.first().getFileInfo().fileName
                                                + "content -> " + returnedOutcome.first().getFileInfo().fileContent
                                                + "type -> " + returnedOutcome.first().getFileInfo().fileType
                                                + "Issues ::\n" + returnedOutcome.first().getMessages().joinToString { "\n" })
                                        props.addManuallyEnteredFileValidationOutcome(returnedOutcome.first())
                                        setState {
                                            validating = false
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.resourceEntryFieldComponent(handler: ResourceEntryFieldProps.() -> Unit): ReactElement {
    return child(ResourceEntryFieldComponent::class) {
        this.attrs(handler)
    }
}
