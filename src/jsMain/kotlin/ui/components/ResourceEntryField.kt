package ui.components

import api.sendValidationRequest
import constants.FhirFormat
import css.FABStyle
import css.FileUploadStyle
import css.ManualEntryStyle
import kotlinx.browser.document
import kotlinx.coroutines.launch
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import mainScope
import model.CliContext
import model.ValidationOutcome
import org.w3c.dom.HTMLTextAreaElement
import react.*
import styled.css
import styled.styledDiv
import styled.styledImg
import styled.styledTextArea
import utils.assembleRequest

external interface ResourceEntryFieldProps : RProps {
    var cliContext: CliContext
    var addManuallyEnteredFileValidationOutcome: (ValidationOutcome) -> Unit
}

class ResourceEntryFieldComponent : RComponent<ResourceEntryFieldProps, RState>() {
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
                    +FileUploadStyle.buttonContainer
                }
                styledDiv {
                    css {
                        +FABStyle.fab
                    }
                    styledImg {
                        css {

                        }
                        attrs {
                            src = "images/validate.svg"
                        }
                    }
                    attrs {
                        onClickFunction = {
                            val field = document.getElementById("inputTextArea") as HTMLTextAreaElement
                            mainScope.launch {
                                val returnedOutcome = sendValidationRequest(
                                    assembleRequest(
                                        cliContext = props.cliContext,
                                        fileName = "",
                                        fileContent = field.value,
                                        fileType = FhirFormat.JSON
                                    )
                                )
                                props.addManuallyEnteredFileValidationOutcome(returnedOutcome.first())
                                println("Validation result for: ${returnedOutcome.first().getFileInfo().fileName}")
                                returnedOutcome.first().getIssues().forEach { vi ->
                                    println("${vi.getSeverity()} :: ${vi.getDetails()}")
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
