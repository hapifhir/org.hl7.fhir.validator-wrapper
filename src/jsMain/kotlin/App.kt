import css.component.LandingPageStyle
import kotlinx.coroutines.MainScope
import kotlinx.css.FlexDirection
import kotlinx.css.flexDirection
import model.AppScreen
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import reactredux.containers.contextSettings
import reactredux.containers.header
import styled.css
import styled.styledDiv
import ui.components.main.sectionTitle
import ui.components.tabLayout

external interface AppProps : RProps {
    var appScreen: AppScreen
    var polyglot: Polyglot
}

val mainScope = MainScope()

class App : RComponent<AppProps, RState>() {
    override fun RBuilder.render() {

        styledDiv {
            css {
                +LandingPageStyle.mainDiv
                flexDirection = FlexDirection.column
            }
            header {}
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
                    contextSettings {}
                }
            }
        }
    }
}


