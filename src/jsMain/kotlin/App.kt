import css.HeaderStyle
import css.LandingPageStyle
import css.TextStyle
import css.const.PADDING_L
import css.const.PADDING_XXL
import kotlinx.coroutines.MainScope
import kotlinx.css.*
import model.AppScreen
import react.*
import reactredux.containers.header
import styled.css
import styled.injectGlobal
import styled.styledDiv
import styled.styledH1
import ui.components.tabLayout

external interface AppProps : RProps {
    var appScreen: AppScreen
}

class AppState : RState { }

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
                    styledDiv {
                        css {
                            textAlign = TextAlign.center
                            marginTop = 2.rem
                            marginBottom = 3.rem
                            justifyContent = JustifyContent.center
                        }
                        styledH1 {
                            css {
                                +TextStyle.h1
                            }
                            +"Validate Resources"
                        }
                    }
                    tabLayout {

                    }
                }
                AppScreen.SETTINGS -> {

                }
            }
        }
    }
}


