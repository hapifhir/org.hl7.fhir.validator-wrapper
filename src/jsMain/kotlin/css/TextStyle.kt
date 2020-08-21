package css

import css.const.ALMOST_RED
import css.const.GRAY_700
import css.const.GRAY_800
import css.const.TRULY_RED
import kotlinx.css.*
import styled.StyleSheet

object TextStyle : StyleSheet("Tabs", isStatic = true) {

    val FONT_FAMILY = "Montserrat"

    val h1 by css {
        fontFamily = FONT_FAMILY
        fontSize = 32.pt
        fontWeight = FontWeight.w700
        fontWeight = FontWeight.bold
        color = TRULY_RED
    }
    val h2 by css {
        fontFamily = FONT_FAMILY
        fontSize = 24.pt
        fontWeight = FontWeight.w500
        color = TRULY_RED
    }
    val h3 by css {
        fontFamily = FONT_FAMILY
        fontSize = 16.pt
        fontWeight = FontWeight.w500
        color = GRAY_700
    }


    val tab by css {
        fontFamily = FONT_FAMILY
        fontSize = 12.pt
        fontWeight = FontWeight.w600
    }

    val tabActive by css {
        color = TRULY_RED
        +tab
    }

    val tabInactive by css {
        color = GRAY_800
        +tab
    }

    val tabHover by css {
        color = ALMOST_RED
        +tab
    }
}