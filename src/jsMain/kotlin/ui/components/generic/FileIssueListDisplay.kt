package ui.components.generic

import css.component.FileErrorDisplayStyle
import css.text.TextStyle
import kotlinx.css.*
import model.ValidationOutcome
import react.*
import styled.css
import styled.styledDiv
import styled.styledImg
import styled.styledP
import ui.components.messageDisplayComponent

external interface FileIssueListDisplayProps : RProps {
    var validationOutcome: ValidationOutcome
}

class FileIssueListDisplayState : RState {

}

class FileIssueListDisplayComponent : RComponent<FileIssueListDisplayProps, FileIssueListDisplayState>() {
    init {
        state = FileIssueListDisplayState()
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                +FileErrorDisplayStyle.mainDivList
            }
            if (props.validationOutcome.getMessages().isNotEmpty()) {
                props.validationOutcome.getMessages().sortedBy { it.getLine() }.forEach {
                    messageDisplayComponent {
                        validationMessage = it
                    }
                }
            } else {
                styledDiv {
                    css {

                    }
                    styledImg {
                        css {
                            display = Display.block
                            marginLeft = LinearDimension.auto
                            marginRight = LinearDimension.auto
                            width = 128.px
                        }
                        attrs {
                            src = "images/smile_racoon.png"
                        }
                    }
                    styledP {
                        css {
                            textAlign = TextAlign.center
                            +TextStyle.h3
                        }
                        if (props.validationOutcome.isValidated()) {
                            +"Smile! There are no issues to display!"
                        } else {
                            +"Press the arrow button to validate your file!"
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.fileIssueListDisplayComponent(handler: FileIssueListDisplayProps.() -> Unit): ReactElement {
    return child(FileIssueListDisplayComponent::class) {
        this.attrs(handler)
    }
}

