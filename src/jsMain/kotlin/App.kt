import kotlinx.coroutines.MainScope
import kotlinx.css.*
import model.AppScreen
import react.*
import reactredux.containers.optionsPage
import reactredux.containers.header
import styled.StyleSheet
import styled.css
import styled.styledDiv
import ui.components.footer.footer
import ui.components.header.HeaderStyle
import ui.components.main.sectionTitle
import ui.components.tabs.tabLayout
import kotlinx.browser.window
import utils.Language

external interface AppProps : Props {
    var appScreen: AppScreen
    var polyglot: Polyglot

    var fetchPolyglot:  (String) -> Unit
    var setLanguage: (Language) -> Unit
}

val mainScope = MainScope()

fun languageSetup(props: AppProps) {
    for (item in window.navigator.languages) {
        val prefix = item.substring(0, 2)
        console.log(prefix)
        for (language in Language.values()) {
            if (prefix == language.code) {
                props.setLanguage(language)
                props.fetchPolyglot(language.code);
                break
            }
        }
        break
    }
}


class App(props : AppProps) : RComponent<AppProps, State>() {
    init {
        languageSetup(props)
    }
    override fun RBuilder.render() {

        styledDiv {
            css {
                +LandingPageStyle.mainDiv
            }
            header {}
            styledDiv {
                css {
                    paddingTop = HeaderStyle.HEADER_HEIGHT
                    display = Display.flex
                    flexDirection = FlexDirection.column
                    flex(flexGrow = 1.0, flexShrink = 1.0, flexBasis = FlexBasis.auto)
                }
                when (props.appScreen) {
                    AppScreen.VALIDATOR -> {
                        sectionTitle {
                            majorText = props.polyglot.t("appscreen_validator_major")
                            minorText = props.polyglot.t("appscreen_validator_minor")
                        }
                        tabLayout {
                            polyglot = props.polyglot
                        }
                    }
                    AppScreen.SETTINGS -> {
                        sectionTitle {
                            majorText = props.polyglot.t("appscreen_options_major")
                            minorText = props.polyglot.t("appscreen_options_minor")
                        }
                        optionsPage {}
                    }
                }
            }
            footer {
                polyglot = props.polyglot
            }
        }
    }
}

/**
 * CSS
 */
object LandingPageStyle : StyleSheet("LandingPageStyle", isStatic = true) {
    val mainDiv by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        height = 100.vh
    }
}

