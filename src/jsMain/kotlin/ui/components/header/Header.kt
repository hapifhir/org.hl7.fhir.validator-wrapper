package ui.components.header

import Polyglot
import css.const.HEADER_SHADOW
import css.const.HIGHLIGHT_GRAY
import css.const.WHITE
import kotlinx.browser.document
import kotlinx.css.*
import kotlinx.css.properties.borderBottom
import kotlinx.css.properties.boxShadow
import model.AppScreen
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener
import react.*
import styled.StyleSheet
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
                +HeaderStyle.headerContainer
                if (state.currentScroll > 0) {
                    boxShadow(color = HEADER_SHADOW, offsetX = 0.px, offsetY = 10.px, blurRadius = 10.px)
                }
            }
            styledImg(src = "images/fhir-logo.png") {
                css {
                    +HeaderStyle.headerImage
                }
            }
            styledDiv {
                css {
                    +HeaderStyle.headerButtonsContainer
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

    override fun handleEvent(event: Event) {
        setState {
            currentScroll = document.documentElement?.scrollTop!!
        }
    }
}

/**
 * Convenience method for instantiating the component.
 */
fun RBuilder.header(handler: HeaderProps.() -> Unit): ReactElement {
    return child(Header::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object HeaderStyle : StyleSheet("HeaderStyle", isStatic = true) {
    val HEADER_HEIGHT = 96.px
    val headerContainer by css {
        display = Display.flex
        flexDirection = FlexDirection.row
        justifyContent = JustifyContent.start
        width = 100.pct
        height = HEADER_HEIGHT
        zIndex = 1
        top = 0.px
        position = Position.fixed
        backgroundColor = WHITE
        borderBottom(width = 2.px, style = BorderStyle.solid, color = HIGHLIGHT_GRAY)
    }
    val headerButtonsContainer by css {
        display = Display.flex
        flexDirection = FlexDirection.row
        height = 100.pct
    }
    val headerImage by css {
        height = 48.px
        padding(horizontal = 48.px)
        alignSelf = Align.center
    }
}