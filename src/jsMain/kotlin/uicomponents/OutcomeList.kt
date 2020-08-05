package uicomponents

import model.ValidationOutcome
import react.*
import react.dom.*

/**
 * We need to store the OperationOutcome externally, and pass it as an attribute to the OperationOutcomeList component.
 * React calls these attributes props. If props change in React, the framework will take care of re-rendering of the
 * page for us.
 */
external interface OutcomeListProps: RProps {
    var outcome: ValidationOutcome
}

class OutcomeList: RComponent<OutcomeListProps, RState>() {
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

/**
 * We can use lambdas with receivers to make the component easier to work with.
 * Now, someone can simply include:
 *
 *              validationOutcome {
 *                  outcome = ValidationOutcome()
 *              }
 */
fun RBuilder.validationOutcome(handler: OutcomeListProps.() -> Unit): ReactElement {
    return child(OutcomeList::class) {
        this.attrs(handler)
    }
}