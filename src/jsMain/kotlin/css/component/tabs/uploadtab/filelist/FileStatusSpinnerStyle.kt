package css.component.tabs.uploadtab.filelist

import css.component.FileItemStyle
import css.const.*
import kotlinx.css.*
import kotlinx.css.properties.*
import styled.StyleSheet
import styled.animation

object FileStatusSpinnerStyle : StyleSheet("FileStatusSpinnerStyle", isStatic = true) {

    val loadingIndicator by css {
        display = Display.inlineBlock
        boxSizing = BoxSizing.borderBox
        width = 100.pct
        height = 100.pct
        borderRadius = 50.pct
        border(width = 2.px, style = BorderStyle.solid, color = HIGHLIGHT_GRAY)
        borderTop(width = 2.px, style = BorderStyle.solid, color = BORDER_GRAY)
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



    //TODO move this to another file

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
}