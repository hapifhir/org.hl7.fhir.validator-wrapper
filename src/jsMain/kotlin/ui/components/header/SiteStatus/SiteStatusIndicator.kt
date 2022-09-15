package ui.components.header.SiteStatus

import css.const.BORDER_GRAY
import css.const.FATAL_PINK
import css.const.HIGHLIGHT_GRAY
import css.const.SUCCESS_GREEN
import kotlinx.css.*
import kotlinx.css.properties.*
import react.*
import styled.StyleSheet
import styled.animation
import styled.css
import styled.styledDiv

enum class SiteState {
    IN_PROGESS,
    UP,
    DOWN
}

external interface SiteStatusIndicatorProps : Props {
    var siteState: SiteState
}

/**
 * Graphical indicator for outcome of validation process.
 */
class SiteStatusIndicator : RComponent<SiteStatusIndicatorProps, State>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                when (props.siteState) {
                    SiteState.UP -> +SiteStatusIndicatorStyle.indicatorGood
                    SiteState.DOWN -> +SiteStatusIndicatorStyle.indicatorError
                    else -> +SiteStatusIndicatorStyle.loadingIndicator
                }
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.siteStatusIndicator(handler: SiteStatusIndicatorProps.() -> Unit) {
    return child(SiteStatusIndicator::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object SiteStatusIndicatorStyle : StyleSheet("SiteStatusIndicator", isStatic = true) {

    val indicator by css {
        display = Display.inlineBlock
        alignItems = Align.center
        boxSizing = BoxSizing.borderBox
        justifyContent = JustifyContent.center
        height = 10.px
        width = 10.px
        borderRadius = 50.pct
    }

    val animatedIndicator by css {
        +indicator
        scaleIntro()
    }

    val indicatorUnknown by css {
        +indicator
        boxSizing = BoxSizing.borderBox
        border(
            width = 2.px,
            style = BorderStyle.solid,
            color = BORDER_GRAY)
    }

    val indicatorError by css {
        +animatedIndicator
        backgroundColor = FATAL_PINK
    }

    val indicatorGood by css {
        +animatedIndicator
        backgroundColor = SUCCESS_GREEN
    }

    val loadingIndicator by css {
        +indicator
        border(width = 2.px, style = BorderStyle.solid, color = HIGHLIGHT_GRAY)
        borderTop(width = 2.px, style = BorderStyle.solid, color = BORDER_GRAY)
        spinner()
    }

    fun CssBuilder.spinner() {
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

    fun CssBuilder.scaleOutro() {
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

    fun CssBuilder.scaleIntro() {
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
