package ui.components

import css.component.SectionTitleStyle
import css.text.TextStyle
import kotlinx.css.*
import react.*
import styled.*

external interface SectionTitleProps : RProps {
    var majorText: String
    var minorText: String
}

class SectionTitle : RComponent<SectionTitleProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                +SectionTitleStyle.sectionTitle
            }
            styledSpan {
                css {
                    +TextStyle.h1
                    paddingBottom = 1.rem
                }
                +props.majorText
            }
            styledSpan {
                css {
                    +TextStyle.titleBody
                }
                +props.minorText
            }
        }
    }
}

fun RBuilder.sectionTitle(handler: SectionTitleProps.() -> Unit): ReactElement {
    return child(SectionTitle::class) {
        this.attrs(handler)
    }
}