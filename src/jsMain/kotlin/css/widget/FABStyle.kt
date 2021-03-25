package css.widget

import css.const.ALMOST_RED
import css.const.GRAY_400
import css.const.REALLY_RED
import css.const.TRULY_RED
import kotlinx.css.*
import kotlinx.css.properties.boxShadow
import styled.StyleSheet

object FABStyle : StyleSheet("FAB", isStatic = true) {

    val fab by css {
        backgroundColor = TRULY_RED
        borderStyle = BorderStyle.none
        borderRadius = 50.pct
        width = 3.rem
        height = 3.rem
        boxShadow(color = GRAY_400, offsetX = 2.px, offsetY = 5.px, blurRadius = 5.px)
        marginBottom = 1.rem
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
    val endButtonContainer by css {
        position = Position.absolute
        right = 0.px
        bottom = 0.px
        margin(1.rem)
        display = Display.flex
    }
}