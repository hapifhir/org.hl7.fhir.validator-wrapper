package ui.components.validation.codeissuedisplay

import css.text.TextStyle
import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import model.MessageFilter
import model.ValidationMessage
import model.ValidationOutcome
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import styled.styledSpan

external interface CodeIssueDisplayProps : RProps {
    var validationOutcome: ValidationOutcome
    var messageFilter: MessageFilter
    var highlightedMessages: List<ValidationMessage>
    var onHighlight: (Boolean, List<ValidationMessage>) -> Unit
}

class CodeIssueDisplay : RComponent<CodeIssueDisplayProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                +CodeIssueDisplayStyle.codeIssueDisplayContainer
            }
            val fileAsLines = props.validationOutcome.getFileInfo().fileContent.lines()
            val lineMap = props.validationOutcome.getMessages().groupBy({ it.getLine() }, { it })
            fileAsLines.forEachIndexed { index, text ->
                styledSpan {
                    css {
                        +TextStyle.codeTextLineNumber
                        +CodeIssueDisplayStyle.lineStyle
                        marginRight = 10.px;
                    }
                    +"${index + 1}"
                }
                if (lineMap.containsKey(index + 1) && (props.messageFilter.filter(lineMap[index + 1]).isNotEmpty())) {
                    codeIssue {
                        validationMessages = props.messageFilter.filter(lineMap[index + 1])
                        lineOfText = text
                        highlighted =
                            props.highlightedMessages.intersect(props.messageFilter.filter(lineMap[index + 1]))
                                .isNotEmpty()
                        onMouseOver = { highlighted ->
                            props.onHighlight(highlighted, props.messageFilter.filter(lineMap[index + 1]))
                        }
                    }
                } else {
                    codeLine {
                        lineOfText = text
                    }
                }
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.codeIssueDisplay(handler: CodeIssueDisplayProps.() -> Unit): ReactElement {
    return child(CodeIssueDisplay::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object CodeIssueDisplayStyle : StyleSheet("CodeIssueDisplayStyle", isStatic = true) {
    val codeIssueDisplayContainer by css {
        display =  Display.grid
        gridTemplateColumns = GridTemplateColumns("min-content auto")
        height = 100.pct
        width = 100.pct
    }
    val lineStyle by css {
        display = Display.flowRoot
        whiteSpace = WhiteSpace.preWrap
        overflowWrap = OverflowWrap.breakWord
        wordWrap = WordWrap.breakWord
    }
}