package css

import kotlinext.js.invoke
import kotlinx.css.*
import styled.createGlobalStyle

/**
 * By default, we want to set the global styles for the entire project to 0 px margin and padding.
 */
object GlobalStyles {
    fun inject() {
        val styles = CSSBuilder(allowClasses = false).apply {
            body {
                margin(0.px)
                padding(0.px)
            }
        }

        createGlobalStyle(styles.toString())
    }
}