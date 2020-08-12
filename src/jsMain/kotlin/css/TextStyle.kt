package css

import kotlinx.css.*
import styled.StyleSheet

object TextStyle : StyleSheet("Tabs", isStatic = true) {

    val HL7_RED = Color("#ec2227")
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
        color = HL7_RED
    }
}