package ui.components

import api.sendValidationRequest
import css.FABStyle
import kotlinx.coroutines.launch
import kotlinx.html.js.onClickFunction
import mainScope
import model.CliContext
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
    var addValidationOutcome: (ValidationOutcome) -> Unit
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
                    val request = assembleRequest(props.cliContext, props.uploadedFiles.map { it.getFileInfo() })
                    mainScope.launch {
                        val returnedOutcome = sendValidationRequest(request)
                        returnedOutcome.forEach { vo ->
                            println("Validation result for: ${vo.getFileInfo().fileName}")
                            vo.getMessages().forEach { message ->
                                message.prettyPrint()
                            }
                            props.addValidationOutcome(vo)
                        }
                    }
                }
            }
        }
    }
}