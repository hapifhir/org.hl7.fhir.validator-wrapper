package css

import kotlinx.css.*
import styled.StyleSheet

object TextStyle : StyleSheet("Tabs", isStatic = true) {

    val FONT_FAMILY = "Raleway"

    val h1 by css {
        fontFamily = FONT_FAMILY
        fontSize = 32.pt
        fontStyle = FontStyle.normal
        fontWeight = FontWeight.w400
        color = HL7_RED
    }

    val tab by css {
        fontFamily = FONT_FAMILY
        fontSize = 12.pt
        fontStyle = FontStyle.normal
        fontWeight = FontWeight.w400
    }

    val tabActive by css {
        color = HL7_RED
        fontWeight = FontWeight.bold
        +tab
    }

    val tabInactive by css {
        color = NOT_BLACK
        fontWeight = FontWeight.w400
        +tab
    }

    val tabHover by css {
        color = HL7_RED
        fontWeight = FontWeight.bold
        +tab
    }
}