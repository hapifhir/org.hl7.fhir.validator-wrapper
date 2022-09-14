package ui.components.header

import Polyglot
import api.isPackagesServerUp
import api.isTerminologyServerUp
import css.const.HEADER_SHADOW
import css.const.HIGHLIGHT_GRAY
import css.const.WHITE
import kotlinx.browser.document
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.css.*
import kotlinx.css.properties.borderBottom
import kotlinx.css.properties.boxShadow
import mainScope
import model.AppScreen
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import styled.styledImg
import ui.components.header.SiteStatus.SiteState
import ui.components.header.SiteStatus.siteStatus
import utils.Language
import kotlin.time.ExperimentalTime

external interface HeaderProps : Props {
    var appScreen: AppScreen
    var language: Language
    var polyglot: Polyglot

    var setScreen: (AppScreen) -> Unit
    var setPolyglot: (Polyglot) -> Unit
    var setLanguage: (Language) -> Unit
}

class HeaderState : State {
    var currentScroll: Double = 0.0
    var terminologyServerState = SiteState.IN_PROGESS
    var packageServerState = SiteState.IN_PROGESS
}

class Header (props : HeaderProps): RComponent<HeaderProps, HeaderState>(), EventListener {

    init {
        state = HeaderState()
        document.addEventListener(type = "scroll", callback = this)
        mainScope.launch {
            val terminologyServerUp = isTerminologyServerUp()
            val packageServerUp = isPackagesServerUp()
            setState {
                terminologyServerState = when (terminologyServerUp) {
                    true -> SiteState.UP
                    false -> SiteState.DOWN
                }
                packageServerState = when (packageServerUp) {
                    true -> SiteState.UP
                    false -> SiteState.DOWN
                }
            }
        }
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

            styledDiv {
                css {
                    +HeaderStyle.sideOptions
                }
                styledDiv {
                    css {
                        +HeaderStyle.siteStatusDiv
                    }
                    siteStatus {
                        label = "tx.fhir.org"
                        status = state.terminologyServerState
                    }
                    siteStatus {
                        label = "packages2.fhir.org"
                        status = state.packageServerState
                    }
                }
            }
            /** TODO LOCALIZATION
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
fun RBuilder.header(handler: HeaderProps.() -> Unit) {
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
    val sideOptions by css {
        display = Display.flex
        flexDirection = FlexDirection.row
        justifyContent = JustifyContent.flexEnd
        padding(horizontal = 48.px)
        flex(flexGrow = 1.0)
    }
    val siteStatusDiv by css {
        display = Display.inlineFlex
        flexDirection = FlexDirection.column
        alignSelf = Align.center
    }
}