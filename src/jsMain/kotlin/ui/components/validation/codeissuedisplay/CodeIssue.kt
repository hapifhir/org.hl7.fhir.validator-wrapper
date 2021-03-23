package ui.components.validation.codeissuedisplay

import css.component.FileErrorDisplayStyle
import css.const.*
import css.text.TextStyle
import kotlinx.css.*
import kotlinx.html.js.onMouseOutFunction
import kotlinx.html.js.onMouseOverFunction
import model.IssueSeverity
import model.MessageFilter
import model.ValidationMessage
import react.*
import styled.*
import utils.getHighestIssueSeverity

external interface CodeIssueProps : RProps {
    var validationMessages: List<ValidationMessage>
    var lineOfText: String

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
                    when (getHighestIssueSeverity(props.validationMessages)) {
                        IssueSeverity.INFORMATION -> +CodeIssueStyle.textHighlightInfo
                        IssueSeverity.WARNING -> +CodeIssueStyle.textHighlightWarning
                        IssueSeverity.ERROR -> +CodeIssueStyle.textHighlightError
                        IssueSeverity.FATAL -> +CodeIssueStyle.textHighlightFatal
                        else -> {}
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
        width = 100.pct
        flexDirection = FlexDirection.row
    }
    val lineStyle by css {
        display = Display.flex
        whiteSpace = WhiteSpace.preWrap
    }
    val textHighlight by css {
        +lineStyle
        borderRadius = 3.px
    }
    val textHighlightFatal by css {
        +textHighlight
        backgroundColor = FATAL_PINK.changeAlpha(0.25)
        hover {
            backgroundColor = FATAL_PINK
        }
    }
    val textHighlightError by css {
        +textHighlight
        backgroundColor = ERROR_ORANGE.changeAlpha(0.25)
        hover {
            backgroundColor = ERROR_ORANGE
        }
    }
    val textHighlightWarning by css {
        +textHighlight
        backgroundColor = WARNING_YELLOW.changeAlpha(0.25)
        hover {
            backgroundColor = WARNING_YELLOW
        }
    }
    val textHighlightInfo by css {
        +textHighlight
        backgroundColor = INFO_BLUE.changeAlpha(0.25)
        hover {
            backgroundColor = INFO_BLUE
        }
    }
}

