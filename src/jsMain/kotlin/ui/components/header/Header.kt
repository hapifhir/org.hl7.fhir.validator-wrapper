package ui.components.header

import Polyglot
import css.component.header.HeaderStyle
import kotlinx.browser.document
import model.AppScreen
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener
import react.*
import styled.css
import styled.styledDiv
import styled.styledImg
import utils.Language

external interface HeaderProps : RProps {
    var appScreen: AppScreen
    var language: Language
    var polyglot: Polyglot

    var setScreen: (AppScreen) -> Unit
    var setPolyglot: (Polyglot) -> Unit
    var setLanguage: (Language) -> Unit
}

class HeaderState : RState {
    var currentScroll: Double = 0.0
}

class Header : RComponent<HeaderProps, HeaderState>(), EventListener {

    init {
        state = HeaderState()
        document.addEventListener(type = "scroll", callback = this)
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                +HeaderStyle.headerBar
                if (state.currentScroll > 0) {
                    +HeaderStyle.headerBarScrolled
                }
            }
            styledDiv {
                css {
                    +HeaderStyle.header
                }
                styledImg(src = "images/fhir-logo.png") {
                    css {
                        +HeaderStyle.headerMainImage
                    }
                }
                styledDiv {
                    css {
                        +HeaderStyle.headerMenu
                    }
                    styledDiv {
                        css {
                            +HeaderStyle.menuEntriesContainer
                        }
                        AppScreen.values().forEach { screen ->
                            headerTabButton {
                                label = screen.display
                                selected = props.appScreen == screen
                                onSelected = { buttonLabel ->
                                    AppScreen.fromDisplay(buttonLabel)?.let { it -> props.setScreen(it) }
                                }
                            }
                        }
                    }
                }
                /** TODO LOCALIZATION
                styledDiv {
                css {
                +HeaderStyle.sideOptions
                }
                styledSpan {
                css {
                +TextStyle.headerMenuItem
                }
                +props.polyglot.t("heading_validate") //"Language"
                attrs {
                onClickFunction = {
                //setState {
                println("ON CLICK BUTTON")
                props.setLanguage(if (props.language == Language.US_ENGLISH) Language.MEX_SPANISH else Language.US_ENGLISH)
                var polyglot = Polyglot()
                when (props.language) {
                Language.US_ENGLISH -> polyglot.extend(phrases = js("{" +
                "'heading_validate': 'Validate Resources'," +
                "'test_string': 'Test String'" +
                "}"))
                Language.MEX_SPANISH -> polyglot.extend(phrases = js("{" +
                "'heading_validate': 'Spanish Resources'," +
                "'test_string': 'Spanish String'" +
                "}"))
                }
                props.setPolyglot(polyglot)
                //}
                }
                }
                }
                 **/
            }
        }
    }

    override fun handleEvent(event: Event) {
        setState {
            currentScroll = document.documentElement?.scrollTop!!
        }
    }
}

fun RBuilder.header(handler: HeaderProps.() -> Unit): ReactElement {
    return child(Header::class) {
        this.attrs(handler)
    }
}