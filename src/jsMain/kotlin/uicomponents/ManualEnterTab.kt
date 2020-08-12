package uicomponents

import css.TabBarStyle
import kotlinx.css.Display
import kotlinx.css.display
import react.*
import styled.*

/**
 * We need to store the OperationOutcome externally, and pass it as an attribute to the OperationOutcomeList component.
 * React calls these attributes props. If props change in React, the framework will take care of re-rendering of the
 * page for us.
 */
external interface ManualEnterTabProps: RProps {
    var active: Boolean
}

class ManualEnterTab: RComponent<ManualEnterTabProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                +TabBarStyle.body
                display = if (props.active) Display.block else Display.none
            }
            styledH3 {
                +"Tab 1"
            }
            styledP {
                +"This is the first tab!"
            }
        }
    }
}

/**
 * We can use lambdas with receivers to make the component easier to work with.
 * To include this component in a layout, someone can simply add:
 *
 *              bottomMenu {
 *
 *              }
 */
fun RBuilder.manualEnterTab(handler: ManualEnterTabProps.() -> Unit): ReactElement {
    return child(ManualEnterTab::class) {
        this.attrs(handler)
    }
}