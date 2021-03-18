package css.component.fileupload.filelist

import css.component.fileupload.filelist.FileStatusSpinnerStyle.scaleIntro
import css.const.*
import kotlinx.css.*
import kotlinx.css.properties.*
import styled.StyleSheet
import styled.animation

object FileStatusIndicatorStyle : StyleSheet("FileStatusIndicator", isStatic = true) {

    val indicator by css {
        display = Display.inlineBlock
        alignItems = Align.center
        boxSizing = BoxSizing.borderBox
        justifyContent = JustifyContent.center
        height = 32.px
        width = 32.px
        borderRadius = 50.pct
    }

    val indicatorNoStatus by css {
        +indicator
        boxSizing = BoxSizing.borderBox
        border(
            width = 2.px,
            style = BorderStyle.solid,
            color = BORDER_GREY)
    }

    val indicatorFatal by css {
        +indicator
        backgroundColor = FATAL_PINK
        scaleIntro()
    }

    val indicatorError by css {
        +indicator
        backgroundColor = ERROR_ORANGE
    }

    val indicatorWarning by css {
        +indicator
        backgroundColor = WARNING_YELLOW
    }

    val indicatorInformation by css {
        +indicator
        backgroundColor = SUCCESS_GREEN
    }

    val indicatorGood by css {
        +indicator
        backgroundColor = SUCCESS_GREEN
    }

    val imageContainer by css {
        width = 100.pct
        height = 100.pct
        display = Display.flex
        alignItems = Align.center
        justifyContent = JustifyContent.center
    }

    val indicatorImage by css {
        width = 80.pct
        height = 80.pct
    }

    val loadingIndicator by css {
        +indicator
        border(width = 2.px, style = BorderStyle.solid, color = HIGHLIGHT_GRAY)
        borderTop(width = 2.px, style = BorderStyle.solid, color = BORDER_GREY)
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
            duration = (0.25).s,
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