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

external interface AppProps : Props {
    var appScreen: AppScreen
    var polyglot: Polyglot

    var fetchPolyglot:  (String) -> Unit
}

val mainScope = MainScope()

class App(props : AppProps) : RComponent<AppProps, State>() {
    init {

        // TODO : Get actual locale of the user's browswer
        console.log("LANGUAGE:")
        for (item in window.navigator.languages) {
            console.log(item)
        }
        console.log("LANGUAGE LANGUAGE LANGUAGE: " + window.navigator.language)

        props.fetchPolyglot(window.navigator.language)
        console.log("Locale:" + props.polyglot.locale())
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

