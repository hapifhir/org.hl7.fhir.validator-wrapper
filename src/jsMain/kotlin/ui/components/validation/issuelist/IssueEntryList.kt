package ui.components.validation.issuelist

import css.component.validation.issueslist.IssueEntryListStyle
import model.ValidationOutcome
import react.*
import styled.css
import styled.styledDiv
import styled.styledUl

external interface IssueEntryListProps : RProps {
    var validationOutcome: ValidationOutcome
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
                    issueEntry {
                        validationMessage = filesIterator.next()
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

fun RBuilder.issueEntryList(handler: IssueEntryListProps.() -> Unit): ReactElement {
    return child(IssueEntryList::class) {
        this.attrs(handler)
    }
}
