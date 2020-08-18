package css

import css.const.PADDING_S
import css.const.REALLY_RED
import css.const.TRULY_RED
import kotlinx.css.*
import kotlinx.css.properties.*
import styled.StyleSheet

object FileListStyle : StyleSheet("FileInfo", isStatic = true) {
    val listBackground by FileListStyle.css {
        boxShadow(color = Color("#BBBBBB"), offsetX = 0.px, offsetY = 5.px, blurRadius = 5.px)
        backgroundColor = Color.white
        height = 100.pct
        width = 100.pct
        display = Display.flex
        flex(flexBasis = 100.pct)
        display = Display.flex
    }
    val listContainer by FileListStyle.css {
        flex(flexBasis = 100.pct)
        padding(0.px)
        listStyleType = ListStyleType.none
        paddingLeft = 0.px
    }
    val listSeparator by FileListStyle.css {
        borderBottom(width = 2.px, style = BorderStyle.solid, color = TRULY_RED)
        margin(horizontal = PADDING_S)
    }
}