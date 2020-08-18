package css

import kotlinx.css.*
import styled.StyleSheet

object FileItemStyle : StyleSheet("FileInfo", isStatic = true) {
    val ICON_WIDTH = 32.px
    val ICON_HEIGHT = 32.px
    val LIST_ITEM_PADDING = 4.px

    val listItem by FileItemStyle.css {
        display = Display.flex
        margin(4.px)
        flex(flexBasis = 100.pct)
        flexDirection = FlexDirection.row
        justifyContent = JustifyContent.flexStart
        alignItems = Align.center
    }

    val typeImage by FileItemStyle.css {
        height = ICON_HEIGHT
        width = ICON_WIDTH
        padding(8.px)
    }

    val titleField by FileItemStyle.css {
        flexGrow = 1.0
    }
}