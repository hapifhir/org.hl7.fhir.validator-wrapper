package ui.components.validation.codeissuedisplay

import css.const.*
import css.text.TextStyle
import kotlinx.css.*
import kotlinx.html.js.onMouseOutFunction
import kotlinx.html.js.onMouseOverFunction
import model.IssueSeverity
import model.ValidationMessage
import react.*
import styled.*
import utils.getHighestIssueSeverity

external interface CodeIssueProps : RProps {
    var validationMessages: List<ValidationMessage>
    var lineOfText: String
    var highlighted: Boolean

    var onSelected: () -> Unit
    var onMouseOver: (Boolean) -> Unit
}

class CodeIssue : RComponent<CodeIssueProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                +CodeIssueStyle.codeIssueContainer
            }
            styledSpan {
                css {
                    +TextStyle.codeDark
                    +CodeIssueStyle.lineStyle
                }
                /**
                 * TODO I am unsure how to get HTML to display a string consisting of only whitespace...
                 * for now, we use a non-breaking Space Character at the end of the line to force rendering
                 */
                +(props.lineOfText.replace(props.lineOfText.trim(), "") + "\u00A0")
            }
            styledMark {
                css {
                    +TextStyle.codeTextBase
                    +CodeIssueStyle.textHighlight
                    backgroundColor = when (getHighestIssueSeverity(props.validationMessages)) {
                        IssueSeverity.INFORMATION -> if (props.highlighted) INFO_BLUE else INFO_BLUE.changeAlpha(0.25)
                        IssueSeverity.WARNING -> if (props.highlighted) WARNING_YELLOW else WARNING_YELLOW.changeAlpha(
                            0.25)
                        IssueSeverity.ERROR -> if (props.highlighted) ERROR_ORANGE else ERROR_ORANGE.changeAlpha(0.25)
                        IssueSeverity.FATAL -> if (props.highlighted) FATAL_PINK else FATAL_PINK.changeAlpha(0.25)
                        else -> WHITE
                    }
                }
                attrs {
                    onMouseOverFunction = {
                        props.onMouseOver(true)
                    }
                    onMouseOutFunction = {
                        props.onMouseOver(false)
                    }
                }
                +props.lineOfText.trim()
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.codeIssue(handler: CodeIssueProps.() -> Unit): ReactElement {
    return child(CodeIssue::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object CodeIssueStyle : StyleSheet("CodeIssueStyle", isStatic = true) {
    val codeIssueContainer by css {
        display = Display.flex
        flexDirection = FlexDirection.row
    }
    val lineStyle by css {
        display = Display.flowRoot
        whiteSpace = WhiteSpace.preWrap
        overflowWrap = OverflowWrap.breakWord
        wordWrap = WordWrap.breakWord
    }
    val textHighlight by css {
        +lineStyle
        borderRadius = 3.px
    }
}

