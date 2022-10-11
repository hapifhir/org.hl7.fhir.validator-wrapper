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
        props.fetchPolyglot("en_US")
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
                            majorText = props.polyglot.t("appscreen_validator_major")
                            minorText = props.polyglot.t("appscreen_validator_minor")
                        }
                        tabLayout {
                            enterResourceText = props.polyglot.t("enter_resources_heading")
                            uploadResourcesText = props.polyglot.t("upload_resources_heading")
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

