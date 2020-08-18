package css

import kotlinx.css.*
import kotlinx.css.properties.Transform
import kotlinx.css.properties.Transforms
import kotlinx.css.properties.translateY
import styled.StyleSheet

object FileInfoStyle : StyleSheet("FileInfo", isStatic = true) {
    val ICON_WIDTH = 32.px
    val ICON_HEIGHT = 32.px
    val LIST_ITEM_PADDING = 4.px

    val typeImage by FileInfoStyle.css {
        height = ICON_HEIGHT
        width = ICON_WIDTH
    }

    val listItem by FileInfoStyle.css {
        padding(LIST_ITEM_PADDING)
//        alignItems = Align.center
//        overflow = Overflow.hidden
        backgroundColor = Color.aqua
        display = Display.flex
//        alignItems = Align.center
//        margin(4.px)
    }
}