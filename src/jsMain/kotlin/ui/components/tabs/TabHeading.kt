package ui.components.tabs

import css.text.TextStyle
import kotlinx.css.*
import react.*
import styled.StyleSheet
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
                +HeadingStyle.headingContainer
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

/**
 * React Component Builder
 */
fun RBuilder.tabHeading(handler: TabHeadingProps.() -> Unit): ReactElement {
    return child(TabHeading::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object HeadingStyle : StyleSheet("HeadingStyle", isStatic = true) {
    val headingContainer by HeadingStyle.css {
        display = Display.flex
        padding(vertical = 16.px)
        alignItems = Align.center
    }
}
