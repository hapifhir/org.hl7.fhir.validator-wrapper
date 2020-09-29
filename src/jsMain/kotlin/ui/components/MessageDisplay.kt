package ui.components

import css.component.MessageDisplayStyle
import css.const.BARELY_BLUE
import css.const.OVERT_ORANGE
import css.const.PERFECT_PINK
import css.const.YAPPY_YELLOW
import kotlinx.css.backgroundColor
import kotlinx.css.flex
import kotlinx.css.px
import model.IssueSeverity
import model.ValidationMessage
import react.*
import styled.css
import styled.styledDiv
import styled.styledPre
import styled.styledSpan

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

