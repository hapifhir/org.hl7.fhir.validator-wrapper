package ui.components.tabs

import css.text.TextStyle
import kotlinx.css.*
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import styled.styledSpan

external interface HeadingProps : RProps {
    var text: String
}

/**
 * Heading field for the manual entry and upload tabs
 */
class Heading : RComponent<HeadingProps, RState>() {
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
fun RBuilder.heading(handler: HeadingProps.() -> Unit): ReactElement {
    return child(Heading::class) {
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
