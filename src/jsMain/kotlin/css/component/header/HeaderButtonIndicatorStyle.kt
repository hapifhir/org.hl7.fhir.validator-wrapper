package css.component.header

import css.const.ALMOST_RED
import css.const.HL7_RED
import kotlinx.css.*
import kotlinx.css.properties.*
import styled.StyleSheet

object HeaderButtonIndicatorStyle : StyleSheet("HeaderButtonIndicator", isStatic = true) {

    val headerButtonIndicator by css {
        cursor = Cursor.pointer
        hover {
            color = HL7_RED
            after {
                transform {
                    scaleX(1)
                }
            }
        }
        after {
            display = Display.block
            content = QuotedString("")
            border(width = 1.px, style = BorderStyle.solid, color = HL7_RED)
            transform {
                scaleX(0)
            }
            transition(duration = 250.ms, timing = Timing.easeInOut, delay = 0.ms)
        }
    }

    val headerButtonIndicatorSelected by css {
        cursor = Cursor.pointer
        after {
            display = Display.block
            content = QuotedString("")
            border(width = 1.px, style = BorderStyle.solid, color = HL7_RED)
        }
    }

}