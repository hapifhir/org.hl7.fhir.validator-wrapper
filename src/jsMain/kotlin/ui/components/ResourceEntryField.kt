package ui.components

import api.sendValidationRequest
import constants.FhirFormat
import css.component.FileUploadStyle
import css.component.ResourceEntryStyle
import css.widget.FABStyle
import css.widget.Spinner
import kotlinx.browser.document
import kotlinx.coroutines.launch
import kotlinx.css.Display
import kotlinx.css.JustifyContent
import kotlinx.css.display
import kotlinx.css.justifyContent
import kotlinx.html.id
import kotlinx.html.js.*
import mainScope
import model.CliContext
import model.ValidationOutcome
import org.w3c.dom.HTMLTextAreaElement
import react.*
import react.dom.span
import react.dom.value
import styled.*
import ui.components.generic.fileIssueListDisplayComponent
import utils.assembleRequest
import Polyglot

external interface ResourceEntryFieldProps : RProps {
    var cliContext: CliContext
    var validationOutcome: ValidationOutcome
    var addManuallyEnteredFileValidationOutcome: (ValidationOutcome) -> Unit
    var polyglot: Polyglot
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

            styledTextArea {
                css {
                    display = if (state.codeDisplay) {
                        Display.block
                    } else {
                        Display.none
                    }
                    +ResourceEntryStyle.entryField
                }
                attrs {
                    id = INPUT_TEXT_ID
                    placeholder = props.polyglot.t("test_string")//"Enter Resource Manually"
                    onInputFunction = {
                        val currentEntry = this.value
                        props.validationOutcome.getFileInfo().setFileContent(currentEntry)
                    }
                    if (this.value.isEmpty() && props.validationOutcome.getFileInfo().fileContent.isNotEmpty()) {
                        this.value = props.validationOutcome.getFileInfo().fileContent
                    }
                }
            }
            styledDiv {
                css {
                    display = if (state.codeDisplay) {
                        Display.none
                    } else {
                        Display.block
                    }
                }
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
                                                + "Issues ::\n" + returnedOutcome.first().getMessages()
                                            .joinToString { "\n" })
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
