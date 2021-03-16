package css.text

import css.const.*
import kotlinx.css.*
import kotlinx.css.properties.*
import styled.StyleSheet

object TextStyle : StyleSheet("Tabs", isStatic = true) {

    private const val FONT_FAMILY_MAIN = "Open Sans"
    private const val FONT_FAMILY_CODE = "Courier Prime"

    val headerButtonLabel by css {
        fontFamily = FONT_FAMILY_MAIN
        fontSize = 14.pt
        fontWeight = FontWeight.w400
        color = TEXT_BLACK
    }

    val pageTitle by css {
        fontFamily = FONT_FAMILY_MAIN
        fontSize = 36.pt
        fontWeight = FontWeight.w400
        color = TRULY_RED
    }

    val pageDetails by css {
        fontFamily = FONT_FAMILY_MAIN
        fontSize = 18.pt
        fontWeight = FontWeight.w400
        color = TEXT_BLACK
    }



    // Legacy Values TODO DELETE
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
    val h4 by css {
        fontFamily = FONT_FAMILY_MAIN
        fontSize = 14.pt
        fontWeight = FontWeight.w400
        color = GRAY_600
    }
    val titleBody by css {
        fontFamily = FONT_FAMILY_MAIN
        fontSize = 16.pt
        fontWeight = FontWeight.w400
        color = TRULY_RED
    }
    val code by css {
        fontFamily = FONT_FAMILY_CODE
        fontSize = 1.rem
        fontWeight = FontWeight.w400
    }
    val codeDark by css {
        +code
        color = GRAY_900
    }
    val codeLight by css {
        +code
        color = GRAY_100
    }
    val codeError by css {
        +code
        color = REALLY_RED
    }
    val settingName by css {
        fontFamily = FONT_FAMILY_MAIN
        fontSize = 16.pt
        fontWeight = FontWeight.w400
        color = GRAY_700
    }
    val settingButton by css {
        fontFamily = FONT_FAMILY_MAIN
        fontSize = 16.pt
        fontWeight = FontWeight.w400
        color = WHITE
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
}