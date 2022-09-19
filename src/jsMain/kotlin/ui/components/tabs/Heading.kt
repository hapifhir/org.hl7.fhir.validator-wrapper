package ui.components.tabs

import css.text.TextStyle
import kotlinx.css.*
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import styled.styledSpan

external interface HeadingProps : Props {
    var text: String
}

/**
 * Heading field for the manual entry and upload tabs
 */
class Heading : RComponent<HeadingProps, State>() {
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
fun RBuilder.heading(handler: HeadingProps.() -> Unit) {
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
