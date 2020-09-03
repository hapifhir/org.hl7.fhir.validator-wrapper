package css

import css.const.*
import kotlinx.css.*
import kotlinx.css.properties.*
import styled.StyleSheet
import styled.animation
import styled.keyframes

object FileItemStyle : StyleSheet("FileItemStyle") {

    val INDICATOR_SIZE = 24.px
    val INDICATOR_BORDER_WIDTH = 2.px
    val TOOL_TIP_BORDER_WIDTH = 2.px
    val TOOL_TIP_TEXT_PADDING = 5.px
    val TOOL_TIP_BORDER_RADIUS = 4.px
    val TOOL_TIP_TOP_OFFSET = INDICATOR_SIZE
    val TOOL_TIP_LEFT_OFFSET = INDICATOR_SIZE
    val TOOL_TIP_MARGIN_LEFT = 12.px

    val listItem by css {
        display = Display.flex
        margin(4.px)
        flex(flexBasis = 100.pct)
        flexDirection = FlexDirection.row
        justifyContent = JustifyContent.flexStart
        alignItems = Align.center
    }

    val typeImage by css {
        height = ICON_SMALL_DIM
        width = ICON_SMALL_DIM
        padding(PADDING_S)
    }

    val titleField by css {
        flexGrow = 1.0
        paddingLeft = PADDING_S
    }

    val indicator by css {
        height = INDICATOR_SIZE
        width = INDICATOR_SIZE
        borderRadius = 50.pct
        display = Display.inlineBlock
        marginLeft = PADDING_S
    }

    val indicatorNoStatus by css {
        +indicator
        border(
            width = INDICATOR_BORDER_WIDTH,
            style = BorderStyle.dotted,
            color = GRAY_800)
    }

    val indicatorFatal by css {
        +indicator
        backgroundColor = PERFECT_PINK
        border(
            width = INDICATOR_BORDER_WIDTH,
            style = BorderStyle.solid,
            color = GRAY_800)
    }

    val indicatorError by css {
        +indicator
        backgroundColor = OVERT_ORANGE
        border(
            width = INDICATOR_BORDER_WIDTH,
            style = BorderStyle.solid,
            color = GRAY_800)
    }

    val indicatorWarning by css {
        +indicator
        backgroundColor = YAPPY_YELLOW
        border(
            width = INDICATOR_BORDER_WIDTH,
            style = BorderStyle.solid,
            color = GRAY_800)
    }

    val indicatorInformation by css {
        +indicator
        backgroundColor = BARELY_BLUE
        border(
            width = INDICATOR_BORDER_WIDTH,
            style = BorderStyle.solid,
            color = GRAY_800)
    }

    val indicatorGood by css {
        +indicator
        backgroundColor = GARISH_GREEN
        border(
            width = INDICATOR_BORDER_WIDTH,
            style = BorderStyle.solid,
            color = GRAY_800)
    }

    val toolTipContainer by css {
        position = Position.relative
        backgroundColor = WHITE
        padding(TOOL_TIP_TEXT_PADDING)
        border(
            width = TOOL_TIP_BORDER_WIDTH,
            style = BorderStyle.solid,
            color = GRAY_800,
            borderRadius = TOOL_TIP_BORDER_RADIUS)
        top = TOOL_TIP_TOP_OFFSET
        left = TOOL_TIP_LEFT_OFFSET
        fadeIn()
        opacity = 0
    }

    val toolTipOutput by css {
        padding(0.px)
        margin(0.px)
    }

    val loadingIcon by css {
        +indicator
        border(width = INDICATOR_BORDER_WIDTH, style = BorderStyle.solid, color = GRAY_200, borderRadius = 50.pct)
        borderTop(width = INDICATOR_BORDER_WIDTH, style = BorderStyle.solid, color = GRAY_900)
        spinner()
    }

    fun CSSBuilder.fadeIn() {
        animation(duration = 0.5.s) {
            from { opacity = 0 }
            to { opacity = 1 }
        }
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