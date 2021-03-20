package ui.components.validation.issuelist

import kotlinx.css.*
import model.MessageFilter
import model.ValidationOutcome
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import styled.styledUl

external interface IssueEntryListProps : RProps {
    var validationOutcome: ValidationOutcome
    var messageFilter: MessageFilter
}

/**
 * Component that displays a list of validation messages.
 */
class IssueEntryList : RComponent<IssueEntryListProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                +IssueEntryListStyle.entryListContainer
            }
            styledUl {
                css {
                    +IssueEntryListStyle.entryList
                }
                val filesIterator = props.validationOutcome.getMessages().iterator()
                while (filesIterator.hasNext()) {
                    val message = filesIterator.next()
                    if (props.messageFilter.showEntry(message)) {
                        issueEntry {
                            validationMessage = message
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
fun RBuilder.issueEntryList(handler: IssueEntryListProps.() -> Unit): ReactElement {
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
        flex(flexBasis = 100.pct)
        padding(8.px)
        width = 100.pct
        height = 100.pct
        backgroundColor = Color.white
    }
    val entryList by IssueEntryListStyle.css {
        flex(flexBasis = 100.pct)
        padding(0.px)
        margin(0.px)
    }
    val listSeparator by IssueEntryListStyle.css {
        marginTop = 12.px
    }
}
