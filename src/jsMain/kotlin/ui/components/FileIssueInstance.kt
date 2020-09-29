package ui.components

import css.animation.FadeIn.fadeIn
import css.component.FileErrorDisplayStyle
import css.component.FileItemStyle
import css.text.TextStyle
import kotlinx.css.*
import kotlinx.html.js.onMouseOutFunction
import kotlinx.html.js.onMouseOverFunction
import model.IssueSeverity
import model.ValidationMessage
import react.*
import styled.css
import styled.styledDiv
import styled.styledMark
import styled.styledSpan
import utils.getHighestIssueSeverity

external interface FileIssueInstanceProps : RProps {
    var validationMessages: List<ValidationMessage>
    var lineOfText: String
}

class FileIssueInstanceState : RState {
    var toolTipVisible = false
}

class FileIssueInstanceComponent : RComponent<FileIssueInstanceProps, FileIssueInstanceState>() {
    init {
        state = FileIssueInstanceState()
    }

    override fun RBuilder.render() {
        var issueSeverity = getHighestIssueSeverity(props.validationMessages)
        styledDiv {
            css {
                display = Display.flex
                flexDirection = FlexDirection.row
            }
            styledSpan {
                css {
                    +TextStyle.code
                    +FileErrorDisplayStyle.lineStyle
                }
                /*
                 * TODO I am unsure how to get HTML to display a string consisting of only whitespace...
                 * for now, we use a Zero Width Space Character at the end of the line to force rendering
                 */
                +(props.lineOfText.replace(props.lineOfText.trim(), "") + "\u200B")
            }
            styledMark {
                css {
                    +TextStyle.code
                    when (issueSeverity) {
                        IssueSeverity.INFORMATION -> +FileErrorDisplayStyle.textHighlightInfo
                        IssueSeverity.WARNING -> +FileErrorDisplayStyle.textHighlightWarning
                        IssueSeverity.ERROR -> +FileErrorDisplayStyle.textHighlightError
                        IssueSeverity.FATAL -> +FileErrorDisplayStyle.textHighlightFatal
                        else -> {
                        }
                    }
                }
                attrs {
                    onMouseOverFunction = {
                        setState {
                            toolTipVisible = true
                            println("TRUE")
                        }
                    }
                    onMouseOutFunction = {
                        setState {
                            toolTipVisible = false
                            println("FALSE")
                        }
                    }
                }
                +props.lineOfText.trim()
            }
            styledDiv {
                css {
                    position = Position.absolute
                    display = Display.flex
                    maxWidth = 70.pct
                    padding(FileItemStyle.TOOL_TIP_TEXT_PADDING)
                    top = FileItemStyle.TOOL_TIP_TOP_OFFSET
                    left = FileItemStyle.TOOL_TIP_LEFT_OFFSET
                    fadeIn()
                    opacity = 0

                    if (state.toolTipVisible) {
                        display = Display.inlineBlock
                        opacity = 1
                    } else {
                        display = Display.none
                        opacity = 0
                    }
                }
                props.validationMessages.forEach {
                    messageDisplayComponent {
                        validationMessage = it
                    }
                }
            }
        }
    }
}

fun RBuilder.fileIssueInstanceComponent(handler: FileIssueInstanceProps.() -> Unit): ReactElement {
    return child(FileIssueInstanceComponent::class) {
        this.attrs(handler)
    }
}

