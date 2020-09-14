import css.LandingPageStyle
import kotlinx.coroutines.MainScope
import kotlinx.css.FlexDirection
import kotlinx.css.flexDirection
import model.AppScreen
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import reactredux.containers.header
import styled.css
import styled.injectGlobal
import styled.styledDiv
import ui.components.contextSettings
import ui.components.sectionTitle
import ui.components.tabLayout

external interface AppProps : RProps {
    var appScreen: AppScreen
}

class AppState : RState {}

val mainScope = MainScope()

class App : RComponent<AppProps, AppState>() {
    init {
        injectGlobal(styles.toString())
        state = AppState()
    }

    override fun RBuilder.render() {

        styledDiv {
            css {
                +LandingPageStyle.mainDiv
                flexDirection = FlexDirection.column
            }
            header { }
            when (props.appScreen) {
                AppScreen.VALIDATOR -> {
                    sectionTitle {
                        majorText = "Validate Resources"
                        minorText = "Lorem Ipsum is simply dummy text of the printing and typesetting industry."
                    }
                    tabLayout {

                    }
                }
                AppScreen.SETTINGS -> {
                    sectionTitle {
                        majorText = "Validation Options"
                        minorText = "Lorem Ipsum is simply dummy text of the printing and typesetting industry."
                    }
                    contextSettings {

                    }
                }
            }
        }
    }
}


