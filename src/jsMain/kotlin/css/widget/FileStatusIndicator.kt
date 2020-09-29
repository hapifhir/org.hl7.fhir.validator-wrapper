package css.widget

import css.component.FileItemStyle
import css.const.*
import kotlinx.css.*
import kotlinx.css.properties.*
import styled.StyleSheet

object FileStatusIndicator : StyleSheet("FileStatusIndicator", isStatic = true) {
    val indicator by css {
        height = FileItemStyle.INDICATOR_SIZE
        width = FileItemStyle.INDICATOR_SIZE
        borderRadius = 50.pct
        display = Display.inlineBlock
        marginLeft = PADDING_S
    }

    val indicatorNoStatus by css {
        +indicator
        border(
            width = FileItemStyle.INDICATOR_BORDER_WIDTH,
            style = BorderStyle.dotted,
            color = GRAY_800)
    }

    val indicatorFatal by css {
        +indicator
        backgroundColor = PERFECT_PINK
        border(
            width = FileItemStyle.INDICATOR_BORDER_WIDTH,
            style = BorderStyle.solid,
            color = GRAY_800)
    }

    val indicatorError by css {
        +indicator
        backgroundColor = OVERT_ORANGE
        border(
            width = FileItemStyle.INDICATOR_BORDER_WIDTH,
            style = BorderStyle.solid,
            color = GRAY_800)
    }

    val indicatorWarning by css {
        +indicator
        backgroundColor = YAPPY_YELLOW
        border(
            width = FileItemStyle.INDICATOR_BORDER_WIDTH,
            style = BorderStyle.solid,
            color = GRAY_800)
    }

    val indicatorInformation by css {
        +indicator
        backgroundColor = BARELY_BLUE
        border(
            width = FileItemStyle.INDICATOR_BORDER_WIDTH,
            style = BorderStyle.solid,
            color = GRAY_800)
    }

    val indicatorGood by css {
        +indicator
        backgroundColor = GARISH_GREEN
        border(
            width = FileItemStyle.INDICATOR_BORDER_WIDTH,
            style = BorderStyle.solid,
            color = GRAY_800)
    }

}