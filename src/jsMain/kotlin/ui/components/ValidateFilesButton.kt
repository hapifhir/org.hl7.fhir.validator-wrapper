package ui.components

import api.sendValidationRequest
import css.widget.FABStyle
import kotlinx.coroutines.launch
import kotlinx.html.js.onClickFunction
import mainScope
import model.CliContext
import model.FileInfo
import model.ValidationOutcome
import model.prettyPrint
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import styled.css
import styled.styledDiv
import styled.styledImg
import utils.assembleRequest

interface ValidateFilesButtonProps : RProps {
    var cliContext: CliContext
    var uploadedFiles: List<ValidationOutcome>
    var sessionId: String

    var addValidationOutcome: (ValidationOutcome) -> Unit
    var toggleValidationInProgress: (Boolean, FileInfo) -> Unit
    var setSessionId: (String) -> Unit
}

class ValidateFilesButton(props: ValidateFilesButtonProps) : RComponent<ValidateFilesButtonProps, RState>(props) {

    override fun RBuilder.render() {
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
                    props.cliContext.prettyPrint()
                    val request = assembleRequest(props.cliContext, props.uploadedFiles.map { it.getFileInfo() })
                    if (props.sessionId.isNotBlank()) { request.setSessionId(props.sessionId) }

                    // TODO separate coroutine execution of validation
                    props.uploadedFiles.forEach {
                        props.toggleValidationInProgress(true, it.getFileInfo())
                    }
                    mainScope.launch {
                        val validationResponse = sendValidationRequest(request)
                        if (validationResponse.getSessionId().isNotEmpty()) props.setSessionId(validationResponse.getSessionId())
                        val returnedOutcome = validationResponse.getOutcomes().map { it.setValidated(true) }
                        returnedOutcome.forEach { vo ->
                            println("Validation result for: ${vo.getFileInfo().fileName}")
                            vo.getMessages().forEach { message ->
                                message.prettyPrint()
                            }
                            props.addValidationOutcome(vo)
                            props.toggleValidationInProgress(true, vo.getFileInfo())
                        }
                    }
                }
            }
        }
    }
}