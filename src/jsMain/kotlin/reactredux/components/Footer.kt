package reactredux.components

import reactredux.containers.filterLink
import reactredux.enums.VisibilityFilter
import react.RBuilder
import react.dom.div
import react.dom.span

fun RBuilder.footer() =
    div {
        span { +"Show: " }
        filterLink {
            attrs.filter = VisibilityFilter.SHOW_ALL
            +"All"
        }
        filterLink {
            attrs.filter = VisibilityFilter.SHOW_ACTIVE
            +"Active"
        }
        filterLink {
            attrs.filter = VisibilityFilter.SHOW_COMPLETED
            +"Completed"
        }
    }