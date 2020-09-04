package ui.components

import css.TabBarStyle
import kotlinx.css.*
import kotlinx.html.js.onMouseOverFunction
import kotlinx.html.onMouseOver
import react.*
import reactredux.containers.resourceEntryField
import styled.css
import styled.styledDiv
import styled.styledMark
import styled.styledP

external interface ManualEnterTabProps : RProps {
    var active: Boolean
}

class ManualEnterTab : RComponent<ManualEnterTabProps, RState>() {

    override fun RBuilder.render() {
        styledDiv {
            css {
                display = if (props.active) Display.flex else Display.none
                +TabBarStyle.body
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