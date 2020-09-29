package css.widget

import css.component.FileItemStyle
import css.const.ALMOST_RED
import css.const.GRAY_200
import css.const.GRAY_900
import css.const.REALLY_RED
import kotlinx.css.*
import kotlinx.css.properties.*
import styled.StyleSheet
import styled.animation

object Spinner : StyleSheet("Spinner", isStatic = true) {

    val loadingIconDark by css {
        +FileStatusIndicator.indicator
        border(width = FileItemStyle.INDICATOR_BORDER_WIDTH, style = BorderStyle.solid, color = GRAY_200, borderRadius = 50.pct)
        borderTop(width = FileItemStyle.INDICATOR_BORDER_WIDTH, style = BorderStyle.solid, color = GRAY_900)
        spinner()
    }

    val loadingIconLight by css {
        height = 2.rem
        width = 2.rem
        borderRadius = 50.pct
        display = Display.inlineBlock
        marginBottom = 1.rem
        border(width = FileItemStyle.INDICATOR_BORDER_WIDTH, style = BorderStyle.solid, color = ALMOST_RED, borderRadius = 50.pct)
        borderTop(width = FileItemStyle.INDICATOR_BORDER_WIDTH, style = BorderStyle.solid, color = REALLY_RED)
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
}