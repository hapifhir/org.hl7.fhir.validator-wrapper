package css.text

import css.const.*
import kotlinx.css.*
import styled.StyleSheet

object TextStyle : StyleSheet("Tabs", isStatic = true) {

    const val FONT_FAMILY_MAIN = "Open Sans"
    private const val FONT_FAMILY_CODE = "Courier Prime"

    val headerButtonLabel by css {
        fontFamily = FONT_FAMILY_MAIN
        fontSize = 14.pt
        fontWeight = FontWeight.w400
        color = TEXT_BLACK
    }

    // Section title
    val pageTitle by css {
        fontFamily = FONT_FAMILY_MAIN
        fontSize = 36.pt
        fontWeight = FontWeight.w400
        color = TRULY_RED
    }

    // Section Details
    val pageDetails by css {
        fontFamily = FONT_FAMILY_MAIN
        fontSize = 18.pt
        fontWeight = FontWeight.w400
        color = TEXT_BLACK
    }

    // Navigation Tabs
    val tabLabel by css {
        fontFamily = FONT_FAMILY_MAIN
        color = TEXT_BLACK
        fontSize = 12.pt
        textTransform = TextTransform.uppercase
    }

    val tabLabelHover by css {
        fontWeight = FontWeight.w600
        +tabLabel
    }

    val tabLabelActive by css {
        fontWeight = FontWeight.w600
        +tabLabel
    }

    val tabLabelInactive by css {
        fontWeight = FontWeight.w400
        +tabLabel
    }

    val tabSectionHeading by css {
        fontFamily = FONT_FAMILY_MAIN
        fontWeight = FontWeight.w400
        fontSize = 16.pt
        color = TEXT_BLACK
        alignSelf = Align.center
    }

    // Buttons
    val textButtonLabel by css {
        fontFamily = FONT_FAMILY_MAIN
        fontSize = 12.pt
        margin(0.px)
        fontWeight = FontWeight.w400
        alignSelf = Align.center
    }

    val genericButtonLabel by css {
        +textButtonLabel
        color = TEXT_BLACK
    }

    val optionButtonLabel by css {
        fontFamily = FONT_FAMILY_MAIN
        fontSize = 12.pt
        margin(0.px)
        fontWeight = FontWeight.w200
        alignSelf = Align.center
        color = TEXT_BLACK
    }

    val toggleButtonLabel by css {
        fontFamily = FONT_FAMILY_MAIN
        fontSize = 10.pt
        fontWeight = FontWeight.w400
        color = TEXT_BLACK
    }

    // File List Entries
    val fileEntryLabel by css {
        fontFamily = FONT_FAMILY_MAIN
        fontSize = 12.pt
        fontWeight = FontWeight.w600
        alignSelf = Align.center
        color = TEXT_BLACK
    }

    // Validation Issue Entries
    val issueLineAndType by css {
        fontFamily = FONT_FAMILY_CODE
        fontSize = 12.pt
        fontWeight = FontWeight.w600
        color = TEXT_BLACK
    }

    // code
    val codeTextBase by css {
        fontFamily = FONT_FAMILY_CODE
        fontSize = 12.pt
        fontWeight = FontWeight.w100
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