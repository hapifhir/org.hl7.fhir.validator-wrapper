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

external interface AppProps : Props {
    var appScreen: AppScreen
    var polyglot: Polyglot

    var fetchPolyglot:  (String) -> Unit
}

val mainScope = MainScope()

class App(props : AppProps) : RComponent<AppProps, State>() {
    init {
        props.fetchPolyglot("cs")
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
                        //  TODO once localization is updated
                        //  majorText = props.polyglot.t("heading_validate")
                            majorText = "Validate Resources"
                            minorText = "Manually enter, or upload resources for validation."
                        }
                        tabLayout {}
                    }
                    AppScreen.SETTINGS -> {
                        sectionTitle {
                            majorText = "Validation Options"
                            minorText = "Modify setting for validating resources."
                        }
                        optionsPage {}
                    }
                }
            }
            footer { }
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

