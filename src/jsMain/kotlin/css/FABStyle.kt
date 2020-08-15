package css

import kotlinx.css.*
import kotlinx.css.properties.boxShadow
import styled.StyleSheet

object FABStyle : StyleSheet("FAB", isStatic = true) {

    val FAB_DIAMETER = 56.px

    val fab by css {
        backgroundColor = HL7_RED
        borderStyle = BorderStyle.none
        borderRadius = 50.pct
        width = FAB_DIAMETER
        height = FAB_DIAMETER
        boxShadow(color = SURLY_SHADOW, offsetX = 2.px, offsetY = 5.px, blurRadius = 5.px)
        cursor = Cursor.pointer
        hover {
            backgroundColor = FADED_HL7_RED

        }
    }

}