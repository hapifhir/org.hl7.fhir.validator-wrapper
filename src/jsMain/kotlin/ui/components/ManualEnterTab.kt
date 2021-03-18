package ui.components

import css.tabs.TabBarStyle
import kotlinx.css.Display
import kotlinx.css.display
import react.*
import reactredux.containers.resourceEntryField
import styled.css
import styled.styledDiv

external interface ManualEnterTabProps : RProps {
    var active: Boolean
}

class ManualEnterTab : RComponent<ManualEnterTabProps, RState>() {

    override fun RBuilder.render() {
        styledDiv {
            css {
                display = if (props.active) Display.flex else Display.none
                //+TabBarStyle.body
            }
            resourceEntryField { }
        }
    }
}

fun RBuilder.manualEnterTab(handler: ManualEnterTabProps.() -> Unit): ReactElement {
    return child(ManualEnterTab::class) {
        this.attrs(handler)
    }
}