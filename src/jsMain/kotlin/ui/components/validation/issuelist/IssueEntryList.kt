package ui.components.validation.issuelist

import kotlinx.css.*
import model.MessageFilter
import model.ValidationMessage
import model.ValidationOutcome
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import styled.styledUl
import ui.components.ace.scrollToLine
import ui.components.ace.gotoLine

external interface IssueEntryListProps : Props {
    var validationOutcome: ValidationOutcome
    var messageFilter: MessageFilter
    var highlightedMessages: List<ValidationMessage>?
    var onHighlight: ((Boolean, List<ValidationMessage>) -> Unit)?
    var editorRef:RefObject<Nothing>
}

/**
 * Component that displays a list of validation messages.
 */
class IssueEntryList : RComponent<IssueEntryListProps, State>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                +IssueEntryListStyle.entryListContainer
            }
            styledUl {
                css {
                    +IssueEntryListStyle.entryList
                }
                val filesIterator =
                    props.validationOutcome.getMessages().sortedBy(ValidationMessage::getLine).iterator()
                while (filesIterator.hasNext()) {
                    val message = filesIterator.next()
                    if (props.messageFilter.showEntry(message)) {
                        issueEntry {
                            validationMessage = message
                            highlighted = props.highlightedMessages?.contains(message) ?: false
                            onMouseOver = { highlighted ->
                                props.onHighlight?.let { it(highlighted, listOf(message)) }
                            }
                            onMouseDown = {
                                scrollToLine(props.editorRef, message.getLine())
                                gotoLine(props.editorRef, message.getLine())
                            }
                        }
                        if (filesIterator.hasNext()) {
                            styledDiv {
                                css {
                                    +IssueEntryListStyle.listSeparator
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.issueEntryList(handler: IssueEntryListProps.() -> Unit) {
    return child(IssueEntryList::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object IssueEntryListStyle : StyleSheet("IssueEntryListStyle", isStatic = true) {
    val entryListContainer by IssueEntryListStyle.css {
        display = Display.flex
        width = 100.pct
        backgroundColor = Color.white
    }
    val entryList by IssueEntryListStyle.css {
        padding(0.px)
        width = 100.pct
    }
    val listSeparator by IssueEntryListStyle.css {
        marginTop = 12.px
    }
}
