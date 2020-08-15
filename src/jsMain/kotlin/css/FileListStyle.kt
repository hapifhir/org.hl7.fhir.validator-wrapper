package css

import kotlinx.css.*
import kotlinx.css.properties.*
import styled.StyleSheet

object FileListStyle : StyleSheet("FileInfo", isStatic = true) {
    val ICON_WIDTH = 32.px
    val ICON_HEIGHT = 32.px
    val LIST_ITEM_PADDING = 4.px

    val listBackground by FileListStyle.css {
        boxShadow(color = Color("#BBBBBB"), offsetX = 0.px, offsetY = 5.px, blurRadius = 5.px)
        backgroundColor = Color.white
        height = 80.pct
        width = 80.pct
    }
    val listContainer by FileListStyle.css {
        listStyleType = ListStyleType.none
        paddingLeft = 0.px
    }
}