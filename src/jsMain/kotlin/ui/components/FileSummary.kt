package ui.components

import css.component.FileSummaryStyle
import kotlinx.css.Display
import kotlinx.css.display
import model.ValidationOutcome
import react.*
import styled.css
import styled.styledDiv
import styled.styledHr

external interface FileSummaryProps : RProps {
    var validationOutcome: ValidationOutcome
    var active: Boolean
    var onClose: () -> Unit
}

class FileSummaryState : RState {
    var codeDisplay = true
}

class FileSummaryComponent : RComponent<FileSummaryProps, FileSummaryState>() {

    init {
        state = FileSummaryState()
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                +FileSummaryStyle.overlay
                display = if (props.active) Display.flex else Display.none
            }
            styledDiv {
                css {
                    +FileSummaryStyle.modalContent
                }
                validationResultDisplayMenuComponent {
                    title = props.validationOutcome.getFileInfo().fileName
                    closeButton = true
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
                    close = {
                        println("on close received")
                        props.onClose()
                    }
                }
                styledHr {
                    css {
                        +FileSummaryStyle.horizontalRule
                    }
                }
                if (state.codeDisplay) {
                    fileIssueCodeDisplayComponent {
                        validationOutcome = props.validationOutcome
                    }
                } else {
                    fileIssueListDisplayComponent {
                        validationOutcome = props.validationOutcome
                    }
                }
            }
        }
    }
}

fun RBuilder.fileSummaryComponent(handler: FileSummaryProps.() -> Unit): ReactElement {
    return child(FileSummaryComponent::class) {
        this.attrs(handler)
    }
}
