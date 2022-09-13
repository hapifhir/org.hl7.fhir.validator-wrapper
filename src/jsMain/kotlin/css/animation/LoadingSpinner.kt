package css.animation

import css.const.HIGHLIGHT_GRAY
import css.const.HL7_RED
import kotlinx.css.*
import kotlinx.css.properties.*
import styled.StyleSheet
import styled.animation

object LoadingSpinner : StyleSheet("LoadingSpinner", isStatic = true) {

    val loadingIndicator by css {
        display = Display.inlineBlock
        boxSizing = BoxSizing.borderBox
        borderRadius = 50.pct
        border(width = 3.px, style = BorderStyle.solid, color = HIGHLIGHT_GRAY)
        borderTop(width = 3.px, style = BorderStyle.solid, color = HL7_RED)
        spinner()
    }

    fun CSSBuilder.spinner() {
        animation(
            duration = 2.s,
            timing = Timing.linear,
            iterationCount = IterationCount.infinite
        ) {
            this.run {
                rules.addAll(listOf(
                    0.invoke {
                        transform { rotate(0.deg) }
                    },
                    100.invoke {
                        transform { rotate(360.deg) }
                    }
                ))
            }
        }
    }

    fun CSSBuilder.scaleOutro() {
        animation(
            duration = (0.5).s,
            timing = Timing.materialAcceleration,
        ) {
            this.run {
                rules.addAll(listOf(
                    0.invoke {
                        transform { scale(1) }
                    },
                    100.invoke {
                        transform { scale(0) }
                    }
                ))
            }
        }
    }

    fun CSSBuilder.scaleIntro() {
        animation(
            duration = (0.5).s,
            // elastic animation
            timing = cubicBezier(0.64, 0.57, 0.67, 1.53)
        ) {
            this.run {
                rules.addAll(listOf(
                    0.invoke {
                        transform { scale(0) }
                    },
                    100.invoke {
                        transform { scale(1) }
                    }
                ))
            }
        }
    }
}