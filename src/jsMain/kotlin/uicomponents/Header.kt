package uicomponents

import css.HeaderStyle
import kotlinx.css.*
import kotlinx.html.id
import model.ValidationOutcome
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener
import react.*
import styled.*
import kotlin.browser.document

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

class HeaderState : RState {
    var currentScroll: Double = 0.0
}

class Header : RComponent<FooterProps, HeaderState>(), EventListener {

    init {
        state = HeaderState()
        document.addEventListener(type = "scroll", callback = this)
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                println("render current Scroll == ${state.currentScroll}")
                if (state.currentScroll > 0) {
                    +HeaderStyle.headerBarScrolled
                } else {
                    +HeaderStyle.headerBarTop
                }
            }
            attrs {
                id = "HeaderView"
            }
            styledImg (src = "images/fhir-logo.png") {
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
            }
        }
    }

    override fun handleEvent(event: Event) {
        setState {
            currentScroll = document.documentElement?.scrollTop!!
            println("setting current scroll -> ${document.documentElement?.scrollTop!!}")
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