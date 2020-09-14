package css

import css.const.*
import kotlinx.css.*
import kotlinx.css.properties.boxShadow
import styled.StyleSheet

object FABStyle : StyleSheet("FAB", isStatic = true) {

    val FAB_DIAMETER = 56.px

    val fab by css {
        backgroundColor = TRULY_RED
        borderStyle = BorderStyle.none
        borderRadius = 50.pct
        width = FAB_DIAMETER
        height = FAB_DIAMETER
        boxShadow(color = GRAY_400, offsetX = 2.px, offsetY = 5.px, blurRadius = 5.px)
        marginBottom = PADDING_XS
        display = Display.flex
        justifyContent = JustifyContent.center
        cursor = Cursor.pointer
        hover {
            backgroundColor = ALMOST_RED
        }
        active {
            backgroundColor = REALLY_RED
        }
    }

}