import context.AppScreenProvider
import context.LocalizationProvider
import context.ValidationProvider
import css.GlobalStyles
import web.dom.document
import react.*
import react.dom.client.createRoot

fun main() {
    // Set all margin and padding to 0 px by default
    GlobalStyles.applyGlobalStyle()

    /**
     * In our main index.html file within the commonMain module, we define a main div with the id "root".
     * This is where we dynamically add all generated ui elements.
     */

    val container = document.getElementById("root")!!
    createRoot(container).render(
        createElement(
            AppScreenProvider::class.js.unsafeCast<ElementType<PropsWithChildren>>(),
            js("{}"),
            createElement(
                LocalizationProvider::class.js.unsafeCast<ElementType<PropsWithChildren>>(),
                js("{}"),
                createElement(
                    ValidationProvider::class.js.unsafeCast<ElementType<PropsWithChildren>>(),
                    js("{}"),
                    createElement(App::class.js.unsafeCast<ElementType<Props>>(), js("{}"))
                )
            )
        )
    )
}
