import css.GlobalStyles
import kotlinx.browser.document
import react.dom.render
import react.redux.provider
import reactredux.containers.app
import reactredux.store.myStore

fun main() {
    // Set all margin and padding to 0 px by default
    GlobalStyles.inject()

    /**
     * In our main index.html file within the commonMain module, we define a main div with the id "root".
     * This is where we dynamically add all generated ui elements.
     */
    document.getElementById("root")?.let {
        render(it) {
        provider(myStore) {
            app { }
        }
    }
    }
}
