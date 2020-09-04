package ui.components

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
                display = Display.inlineFlex
                flexDirection = FlexDirection.row
                minHeight = 64.px
                width = 100.pct
                backgroundColor = GRAY_200
                boxShadow(color = SHADOW, offsetX = 0.px, offsetY = 5.px, blurRadius = 5.px)
                marginBottom = 10.px
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
                    padding(6.px)
                    flex(flexBasis = FlexBasis.auto)
                    alignSelf = Align.center
                    +TextStyle.toolTipTextBold
                }
                +"${props.validationMessage.getLevel().display}\nLine: ${props.validationMessage.getLine()}"
            }

            styledSpan {
                css {
                    padding(6.px)
                    flex(flexBasis = 100.pct)
                    alignSelf = Align.center
                    overflowWrap = OverflowWrap.breakWord
                    +TextStyle.code
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

