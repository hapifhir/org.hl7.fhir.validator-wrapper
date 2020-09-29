package css.component

import css.animation.FadeIn.fadeIn
import css.const.*
import kotlinx.css.*
import kotlinx.css.properties.*
import styled.StyleSheet

object FileItemStyle : StyleSheet("FileItemStyle") {

    val INDICATOR_SIZE = 24.px
    val INDICATOR_BORDER_WIDTH = 2.px
    val TOOL_TIP_BORDER_WIDTH = 2.px
    val TOOL_TIP_TEXT_PADDING = 5.px
    val TOOL_TIP_BORDER_RADIUS = 4.px
    val TOOL_TIP_TOP_OFFSET = INDICATOR_SIZE
    val TOOL_TIP_LEFT_OFFSET = INDICATOR_SIZE

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

}