package css.animation

import kotlinx.css.CssBuilder
import kotlinx.css.opacity
import kotlinx.css.properties.ms
import kotlinx.css.properties.s
import styled.StyleSheet
import styled.animation

object FadeIn : StyleSheet("FadeIn", isStatic = true) {
    fun CssBuilder.quickFadeIn() {
        animation(duration = 500.ms) {
            from { opacity = 0 }
            to { opacity = 1 }
        }
    }

    fun CssBuilder.fadeIn() {
        animation(duration = 1.s) {
            from { opacity = 0 }
            to { opacity = 1 }
        }
    }
}