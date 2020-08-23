package ui.components

import css.HeaderStyle
import kotlinx.browser.document
import kotlinx.html.id
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener
import react.*
import styled.css
import styled.styledDiv
import styled.styledImg

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
                +HeaderStyle.header
                if (state.currentScroll > 0) {
                    +HeaderStyle.headerBarScrolled
                }
            }
            attrs {
                id = "HeaderView"
            }
            styledImg(src = "images/fhir-logo.png") {
                attrs {

                }
                css {
                    +HeaderStyle.headerMainImage
                }
            }
            styledDiv {
                css {
                    +HeaderStyle.headerFiller
                }
            }
        }
    }

    override fun handleEvent(event: Event) {
        setState {
            currentScroll = document.documentElement?.scrollTop!!
        }
    }
}

fun RBuilder.header(handler: FooterProps.() -> Unit): ReactElement {
    return child(Header::class) {
        this.attrs(handler)
    }
}