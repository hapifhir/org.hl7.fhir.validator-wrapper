import react.dom.render
import kotlinx.browser.document
import kotlinx.browser.window
import react.redux.provider
import reactredux.store.store

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
