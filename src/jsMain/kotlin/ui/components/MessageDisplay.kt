package ui.components

import css.MessageDisplayStyle
import css.TextStyle
import css.const.*
import kotlinx.css.*
import kotlinx.css.properties.boxShadow
import model.IssueSeverity
import model.ValidationMessage
import react.*
import styled.*
import styled.styledPre

external interface MessageDisplayProps : RProps {
    var validationMessage: ValidationMessage
}

class MessageDisplayComponent : RComponent<MessageDisplayProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                +MessageDisplayStyle.messageLayout
            }

            styledDiv {
                css {
                    flex(flexBasis = 12.px)
                    when (props.validationMessage.getLevel()) {
                        IssueSeverity.INFORMATION -> backgroundColor = BARELY_BLUE
                        IssueSeverity.WARNING -> backgroundColor = YAPPY_YELLOW
                        IssueSeverity.ERROR -> backgroundColor = OVERT_ORANGE
                        IssueSeverity.FATAL -> backgroundColor = PERFECT_PINK
                        else -> {
                        }
                    }
                }
            }

            styledPre {
                css {
                    +MessageDisplayStyle.levelAndLineNumber
                }
                +"${props.validationMessage.getLevel().display}\nLine: ${props.validationMessage.getLine()}"
            }

            styledSpan {
                css {
                    +MessageDisplayStyle.messageDetails
                }
                +props.validationMessage.getMessage()
            }
        }
    }
}

fun RBuilder.messageDisplayComponent(handler: MessageDisplayProps.() -> Unit): ReactElement {
    return child(MessageDisplayComponent::class) {
        this.attrs(handler)
    }
}

