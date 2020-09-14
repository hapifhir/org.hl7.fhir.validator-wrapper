package ui.components

import kotlinx.css.*
import kotlinx.html.classes
import model.ValidationOutcome
import react.*
import react.dom.div
import styled.StyleSheet
import styled.css
import styled.styledDiv

external interface FooterProps : RProps {
    var outcome: ValidationOutcome
}

object FooterColumnStyle : StyleSheet("FooterColumnStyle", isStatic = true) {
    val footerColumn by css {
        width = 33.pct
        minHeight = 40.px
        float = Float.left
    }
}

class Footer : RComponent<FooterProps, RState>() {
    override fun RBuilder.render() {
        div {
            styledDiv {
                attrs {
                    classes = setOf("menuColumn")
                }
                css {
                    +FooterColumnStyle.footerColumn
                    backgroundColor = Color.blueViolet
                }
            }
            styledDiv {
                attrs {
                    classes = setOf("menuColumn")
                }
                css {
                    +FooterColumnStyle.footerColumn
                    backgroundColor = Color.darkRed
                }
            }
            styledDiv {
                attrs {
                    classes = setOf("menuColumn")
                }
                css {
                    +FooterColumnStyle.footerColumn
                    backgroundColor = Color.coral
                }
            }
        }
    }
}

fun RBuilder.footer(handler: FooterProps.() -> Unit): ReactElement {
    return child(Footer::class) {
        this.attrs(handler)
    }
}