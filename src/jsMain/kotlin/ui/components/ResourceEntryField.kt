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
import kotlinx.html.js.onClickFunction
import mainScope
import model.CliContext
import model.ValidationOutcome
import org.w3c.dom.HTMLTextAreaElement
import react.*
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
                        id = "inputTextArea"
                        placeholder = "Enter Resource Manually"
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
                                    val field = document.getElementById("inputTextArea") as HTMLTextAreaElement
                                    mainScope.launch {
                                        setState {
                                            validating = true
                                        }
                                        val returnedOutcome = sendValidationRequest(
                                            assembleRequest(
                                                cliContext = props.cliContext,
                                                fileName = "",
                                                fileContent = field.value,
                                                fileType = FhirFormat.JSON
                                            )
                                        )
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
