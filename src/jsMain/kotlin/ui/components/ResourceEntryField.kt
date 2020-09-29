package ui.components

import api.sendValidationRequest
import constants.FhirFormat
import css.widget.FABStyle
import css.component.FileItemStyle
import css.component.FileUploadStyle
import css.component.ManualEntryStyle
import css.widget.Spinner
import kotlinx.browser.document
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.css.*
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import mainScope
import model.CliContext
import model.ValidationOutcome
import model.prettyPrint
import org.w3c.dom.HTMLTextAreaElement
import react.*
import styled.*
import utils.assembleRequest

external interface ResourceEntryFieldProps : RProps {
    var cliContext: CliContext
    var validationOutcome: ValidationOutcome
    var addManuallyEnteredFileValidationOutcome: (ValidationOutcome) -> Unit
}

class ResourceEntryFieldState : RState {
    var validating: Boolean = false
}

class ResourceEntryFieldComponent : RComponent<ResourceEntryFieldProps, ResourceEntryFieldState>() {

    init {
        state = ResourceEntryFieldState()
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                +ManualEntryStyle.entryContainer
            }
            styledTextArea {
                css {
                    +ManualEntryStyle.entryField
                }
                attrs {
                    id = "inputTextArea"
                    placeholder = "Enter Resource Manually"
                }
            }
            styledDiv {
                css {
                    position = Position.absolute
                    right = 0.px
                    bottom = 0.px
                    margin(1.rem)
                    display = Display.flex
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
                                        delay(10000)
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
                                        println("Validation result for: ${
                                            returnedOutcome.first().getFileInfo().fileName
                                        }")
                                        returnedOutcome.first().getMessages().forEach { message ->
                                            message.prettyPrint()
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
