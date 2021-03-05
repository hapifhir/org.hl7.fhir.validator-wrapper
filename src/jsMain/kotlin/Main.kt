import kotlinx.browser.document
import react.dom.render
import react.redux.provider
import reactredux.containers.app
import reactredux.store.myStore

fun main() {
    render(document.getElementById("root")) {
        provider(myStore) {
            app { }
//            child(App::class) {}
        }
    }
}
