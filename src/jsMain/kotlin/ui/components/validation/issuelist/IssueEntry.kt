package ui.components.validation.issuelist

import css.const.*
import css.text.TextStyle
import css.component.validation.issueslist.IssueEntryStyle
import kotlinx.css.BorderStyle
import kotlinx.css.properties.borderLeft
import kotlinx.css.px
import model.IssueSeverity
import model.ValidationMessage
import react.*
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

fun RBuilder.issueEntry(handler: IssueEntryProps.() -> Unit): ReactElement {
    return child(IssueEntry::class) {
        this.attrs(handler)
    }
}

