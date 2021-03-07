import kotlinx.css.Color
import kotlinx.css.backgroundColor
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.dom.p
import styled.css
import styled.styledDiv
import styled.styledSpan

//external interface AppProps : RProps {
//    var appScreen: AppScreen
//    var polyglot: Polyglot
//}

//val mainScope = MainScope()

class TestApp : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                backgroundColor = Color.burlyWood
            }
            attrs {

            }
            div {
                p { }
            }
            styledSpan {
                +"POO POO"
            }
        }
    }
}


