import react.dom.render
import kotlinx.browser.document
import kotlinx.browser.window
import react.redux.provider
import reactredux.state.AppState
import reactredux.state.rootReducer
import redux.compose
import redux.createStore
import redux.rEnhancer

val store = createStore(
    ::rootReducer,
    AppState(),
    compose(
        rEnhancer(),
        js("if(window.__REDUX_DEVTOOLS_EXTENSION__ )window.__REDUX_DEVTOOLS_EXTENSION__ ();else(function(f){return f;});")
    )
)

fun main() {
    val rootDiv = document.getElementById("root")
    window.onload = {
        render(rootDiv) {
            provider(store) {
                child(App::class) {}
            }
        }
    }
}
