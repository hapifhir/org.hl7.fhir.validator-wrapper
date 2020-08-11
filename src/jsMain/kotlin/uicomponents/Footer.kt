package uicomponents

import kotlinx.html.colorInput
import model.ValidationOutcome
import react.*
import react.dom.*
import kotlinx.css.*
import kotlinx.html.classes
import styled.StyleSheet
import styled.*

/**
 * We need to store the OperationOutcome externally, and pass it as an attribute to the OperationOutcomeList component.
 * React calls these attributes props. If props change in React, the framework will take care of re-rendering of the
 * page for us.
 */
external interface FooterProps: RProps {
    var outcome: ValidationOutcome
}

object FooterColumnStyle : StyleSheet("FooterColumnStyle", isStatic = true) {
    val footerColumn by css {
        width = 33.pct
        minHeight = 40.px
        float = Float.left
    }
}

class Footer: RComponent<FooterProps, RState>() {
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

/**
 * We can use lambdas with receivers to make the component easier to work with.
 * To include this component in a layout, someone can simply add:
 *
 *              bottomMenu {
 *
 *              }
 */
fun RBuilder.footer(handler: FooterProps.() -> Unit): ReactElement {
    return child(Footer::class) {
        this.attrs(handler)
    }
}