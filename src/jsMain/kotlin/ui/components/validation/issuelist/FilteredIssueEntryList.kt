package ui.components.validation.issuelist

import kotlinx.css.*
import model.MessageFilter
import model.ValidationOutcome
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import ui.components.tabs.heading

external interface FilteredIssueEntryListProps : RProps {
    var validationOutcome: ValidationOutcome
}

class FilteredIssueEntryListState : RState {
    var messageFilter = MessageFilter()
}

/**
 * Component that displays a list of validation messages.
 */
class FilteredIssueEntryList : RComponent<FilteredIssueEntryListProps, FilteredIssueEntryListState>() {

    init {
        state = FilteredIssueEntryListState()
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                +FilteredIssueEntryListStyle.entryListContainer
            }
            heading {
                text =
                    "Results (${state.messageFilter.determineNumberDisplayedIssues(props.validationOutcome.getMessages())})"
            }
            issueFilterButtonBar {
                messageFilter = state.messageFilter
                onUpdated = { newMessageFilter ->
                    setState {
                        state.messageFilter = newMessageFilter
                    }
                }
            }
            issueEntryList {
                validationOutcome = props.validationOutcome
                messageFilter = state.messageFilter
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.filteredIssueEntryList(handler: FilteredIssueEntryListProps.() -> Unit): ReactElement {
    return child(FilteredIssueEntryList::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object FilteredIssueEntryListStyle : StyleSheet("FilteredIssueEntryListStyle", isStatic = true) {
    val entryListContainer by FilteredIssueEntryListStyle.css {
        display = Display.flex
        flexDirection = FlexDirection.column
        flex(flexBasis = 100.pct)
        width = 100.pct
        height = 100.pct
        backgroundColor = Color.white
    }
}