package css.text

import css.const.*
import kotlinx.css.*
import kotlinx.css.properties.*
import styled.StyleSheet

object TextStyle : StyleSheet("Tabs", isStatic = true) {

    val FONT_FAMILY_MAIN = "Montserrat"
    val FONT_FAMILY_CODE = "Source Code Pro"

    val h1 by css {
        fontFamily = FONT_FAMILY_MAIN
        fontSize = 40.pt
        fontWeight = FontWeight.w600
        color = TRULY_RED
    }
    val h2 by css {
        fontFamily = FONT_FAMILY_MAIN
        fontSize = 24.pt
        fontWeight = FontWeight.w500
        color = TRULY_RED
    }
    val h3 by css {
        fontFamily = FONT_FAMILY_MAIN
        fontSize = 16.pt
        fontWeight = FontWeight.w500
        color = GRAY_700
    }
    val titleBody by css {
        fontFamily = FONT_FAMILY_MAIN
        fontSize = 16.pt
        fontWeight = FontWeight.w400
        color = TRULY_RED
    }
    val code by css {
        fontFamily = FONT_FAMILY_CODE
        fontSize = 10.pt
        fontWeight = FontWeight.w400
        color = GRAY_900
    }
    val settingName by css {
        fontFamily = FONT_FAMILY_MAIN
        fontSize = 16.pt
        fontWeight = FontWeight.w400
        color = GRAY_700
    }
    val tab by css {
        fontFamily = FONT_FAMILY_MAIN
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

    val toolTipText by css {
        color = GRAY_800
        fontFamily = FONT_FAMILY_MAIN
        fontSize = 10.pt
        fontWeight = FontWeight.w400
        textAlign = TextAlign.start
    }

    val toolTipTextBold by css {
        color = GRAY_800
        fontFamily = FONT_FAMILY_MAIN
        fontSize = 10.pt
        fontWeight = FontWeight.w700
        textAlign = TextAlign.start
    }

    val headerMenuItem by css {
        cursor = Cursor.pointer
        color = GRAY_800
        fontFamily = FONT_FAMILY_MAIN
        fontSize = 16.pt
        fontWeight = FontWeight.w400
        textAlign = TextAlign.center
        hover {
            color = ALMOST_RED
            after {
                transform {
                    scaleX(1)
                }
            }
        }
        after {
            display = Display.block
            content = QuotedString("")
            border(width = 1.px, style = BorderStyle.solid, color = ALMOST_RED)
            transform {
                scaleX(0)
            }
            transition(duration = 250.ms, timing = Timing.easeInOut, delay = 0.ms)
        }
    }

    val headerMenuItemSelected by css {
        +headerMenuItem
        color = TRULY_RED
    }

}