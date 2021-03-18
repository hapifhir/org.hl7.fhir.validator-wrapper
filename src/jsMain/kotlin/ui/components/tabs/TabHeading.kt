package ui.components.tabs

import css.tabs.TabStyle
import css.text.TextStyle
import react.*
import styled.css
import styled.styledDiv
import styled.styledSpan

external interface TabHeadingProps : RProps {
    var text: String
}

/**
 * Heading field for the manual entry and upload tabs
 */
class TabHeading : RComponent<TabHeadingProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                +TabStyle.headingContainer
            }
            styledSpan {
                css {
                    +TextStyle.tabSectionHeading
                }
                +props.text
            }
        }
    }
}

fun RBuilder.tabHeading(handler: TabHeadingProps.() -> Unit): ReactElement {
    return child(TabHeading::class) {
        this.attrs(handler)
    }
}
