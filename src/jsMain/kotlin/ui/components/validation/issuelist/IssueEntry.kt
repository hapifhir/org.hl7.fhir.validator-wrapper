package ui.components.validation.issuelist

import css.const.*
import css.text.TextStyle
import kotlinx.css.*
import kotlinx.css.properties.borderBottom
import kotlinx.css.properties.borderLeft
import kotlinx.css.properties.borderRight
import kotlinx.css.properties.borderTop
import model.IssueSeverity
import model.ValidationMessage
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import styled.styledSpan

external interface IssueEntryProps : RProps {
    var validationMessage: ValidationMessage
    var onIssueHover: (ValidationMessage) -> Unit
}

/**
 * A single list entry for a validation issue. Displays the type, line number, and explanation.
 */
class IssueEntry : RComponent<IssueEntryProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                +IssueEntryStyle.issueContainer
                borderLeft(width = 4.px, style = BorderStyle.solid,
                    color = when (props.validationMessage.getLevel()) {
                        IssueSeverity.INFORMATION -> INFO_BLUE
                        IssueSeverity.WARNING -> WARNING_YELLOW
                        IssueSeverity.ERROR -> ERROR_ORANGE
                        IssueSeverity.FATAL -> FATAL_PINK
                        else -> BORDER_GRAY
                    })
            }
            styledSpan {
                css {
                    +IssueEntryStyle.levelAndLineNumber
                    +TextStyle.issueLineAndType
                }
                +"${props.validationMessage.getLevel().display} Line: ${props.validationMessage.getLine()}"
            }
            styledSpan {
                css {
                    +IssueEntryStyle.messageDetails
                    +TextStyle.codeTextBase
                }
                +props.validationMessage.getMessage()
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.issueEntry(handler: IssueEntryProps.() -> Unit): ReactElement {
    return child(IssueEntry::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object IssueEntryStyle : StyleSheet("IssueEntryStyle", isStatic = true) {
    val issueContainer by css {
        display = Display.flex
        flexDirection = FlexDirection.row
        minHeight = 64.px
        width = 100.pct
        backgroundColor = WHITE
        borderTop(width = 1.px, style = BorderStyle.solid, color = BORDER_GRAY)
        borderRight(width = 1.px, style = BorderStyle.solid, color = BORDER_GRAY)
        borderBottom(width = 1.px, style = BorderStyle.solid, color = BORDER_GRAY)
        padding(16.px)
        boxSizing = BoxSizing.borderBox
    }
    val levelAndLineNumber by css {
        paddingRight = 16.px
        minWidth = 25.pct
        alignSelf = Align.center
    }
    val messageDetails by css {
        flex(flexBasis = 100.pct)
        alignSelf = Align.center
        overflowWrap = OverflowWrap.breakWord
    }
}