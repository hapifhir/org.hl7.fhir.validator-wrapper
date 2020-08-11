package uicomponents

import kotlinx.css.*
import kotlinx.html.id
import model.ValidationOutcome
import react.*
import react.dom.div
import react.dom.p
import styled.*

val HEADER_HEIGHT = 60.px
val HEADER_WIDTH = 100.pct
val HEADER_PADDING = HEADER_HEIGHT * 0.1

/**
 * We need to store the OperationOutcome externally, and pass it as an attribute to the OperationOutcomeList component.
 * React calls these attributes props. If props change in React, the framework will take care of re-rendering of the
 * page for us.
 */
external interface HeaderProps : RProps {
    var outcome: ValidationOutcome
}

object HeaderStyle : StyleSheet("HeaderStyle", isStatic = true) {
    val header by css {
        width = HEADER_WIDTH
        height = HEADER_HEIGHT
        position = Position.fixed
        top = 0.px
        paddingLeft = HEADER_PADDING
        paddingRight = HEADER_PADDING
        display = Display.flex
        alignItems = Align.center
        backgroundColor = Color.orange
    }

}

class Header : RComponent<FooterProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                +HeaderStyle.header
            }
            attrs {
                id = "HeaderView"
            }
            styledImg (src = "fhir-logo.png") {
                attrs {

                }
                css {
                    height = HEADER_HEIGHT * 0.8
                }
            }
            styledDiv {
                css {
                    width = 100.pct
                    height = HEADER_HEIGHT * 0.8
                    backgroundColor = Color.burlyWood
                }
                styledP {
                    +"Validator"
                    css {
                        fontFamily = "Roboto Condensed"
                    }
                }
            }
        }
    }
}

/**
 * We can use lambdas with receivers to make the component easier to work with.
 * To include this component in a layout, someone can simply add:
 *
 *              header {
 *
 *              }
 */
fun RBuilder.header(handler: FooterProps.() -> Unit): ReactElement {
    return child(Header::class) {
        this.attrs(handler)
    }
}