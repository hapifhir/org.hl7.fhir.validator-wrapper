package ui.components

import model.ValidationOutcome
import react.*
import react.dom.li
import react.dom.ul

external interface OutcomeListProps : RProps {
    var outcome: ValidationOutcome
}

class OutcomeList : RComponent<OutcomeListProps, RState>() {
    override fun RBuilder.render() {
        ul {
            for (issue in props.outcome.getIssues()) {
                li {
                    +"${issue.getSeverity()}: ${issue.getDetails()}"
                }
            }
        }
    }
}

fun RBuilder.validationOutcome(handler: OutcomeListProps.() -> Unit): ReactElement {
    return child(OutcomeList::class) {
        this.attrs(handler)
    }
}