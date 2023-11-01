import css.GlobalStyles
import kotlinx.browser.document
import react.create
import react.dom.client.createRoot

import react.dom.render
import react.redux.Provider
import react.redux.provider
import reactredux.containers.app
import reactredux.store.createStore
import reactredux.store.myStore

fun main() {
    // Set all margin and padding to 0 px by default
    GlobalStyles.applyGlobalStyle()

    /**
     * In our main index.html file within the commonMain module, we define a main div with the id "root".
     * This is where we dynamically add all generated ui elements.
     */

        val container = document.getElementById("root")!!
    createRoot(container).render(Provider.create { // this: {ChildrenBuilder & Props & ProviderProps}
        store = myStore // Set the store. Because it is named ProviderProps.store, you can't use name 'store' for your store any more.
        app {}
    })

}
