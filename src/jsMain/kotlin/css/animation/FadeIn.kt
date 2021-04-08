package css.animation

import kotlinx.css.CSSBuilder
import kotlinx.css.opacity
import kotlinx.css.properties.ms
import kotlinx.css.properties.s
import styled.StyleSheet
import styled.animation

object FadeIn : StyleSheet("FadeIn", isStatic = true) {
    fun CSSBuilder.quickFadeIn() {
        animation(duration = 500.ms) {
            from { opacity = 0 }
            to { opacity = 1 }
        }
    }

    fun CSSBuilder.fadeIn() {
        animation(duration = 1.s) {
            from { opacity = 0 }
            to { opacity = 1 }
        }
    }
}