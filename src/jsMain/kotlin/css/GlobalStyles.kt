package css

import kotlinx.css.*
import styled.injectGlobal

/**
 * By default, we want to set the global styles for the entire project to 0 px margin and padding.
 */
object GlobalStyles {
    fun inject() {
        val styles = CSSBuilder(allowClasses = true).apply {
            body {
                margin(0.px)
                padding(0.px)
            }
            ".editor-focus-error" {
                backgroundColor = rgba(255, 0, 0, 0.4);
                position = Position.absolute;
            }
        }

        injectGlobal(styles.toString())
    }
}

// TODO I'm leaving this here, so I remember how to do it later.
//        ancestorHover(".${ContextSettingsStyle.name}-${ContextSettingsStyle::dropdown.name}") {
//            display = Display.block
//        }