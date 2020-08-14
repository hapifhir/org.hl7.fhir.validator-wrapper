package css

import kotlinx.css.*
import kotlinx.css.properties.Transform
import kotlinx.css.properties.Transforms
import kotlinx.css.properties.translateY
import styled.StyleSheet

object FileListStyle : StyleSheet("FileInfo", isStatic = true) {
    val ICON_WIDTH = 32.px
    val ICON_HEIGHT = 32.px
    val LIST_ITEM_PADDING = 4.px

    val listContainer by FileListStyle.css {
        listStyleType = ListStyleType.none
        paddingLeft = 0.px
    }
}