package css.text

import css.const.HL7_RED
import css.const.SUCCESS_GREEN
import css.const.TEXT_BLACK
import kotlinx.css.*
import styled.StyleSheet

object TextStyle : StyleSheet("Tabs", isStatic = true) {

    const val FONT_FAMILY_MAIN = "Open Sans"
    private const val FONT_FAMILY_CODE = "Courier Prime"

    val headerButtonLabel by css {
        fontFamily = FONT_FAMILY_MAIN
        fontSize = 12.pt
        fontWeight = FontWeight.w400
        color = TEXT_BLACK
    }

    // Section title
    val pageTitle by css {
        fontFamily = FONT_FAMILY_MAIN
        fontSize = 28.pt
        fontWeight = FontWeight.w400
        color = HL7_RED
    }

    // Section Details
    val pageDetails by css {
        fontFamily = FONT_FAMILY_MAIN
        fontSize = 14.pt
        fontWeight = FontWeight.w200
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
        fontSize = 15.pt
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
        fontWeight = FontWeight.lighter
        color = TEXT_BLACK
    }

    // options/settings
    val optionsDetailText by css {
        fontFamily = FONT_FAMILY_MAIN
        fontSize = 11.pt
        fontWeight = FontWeight.w200
        color = TEXT_BLACK
    }
    val optionName by css {
        fontFamily = FONT_FAMILY_MAIN
        fontSize = 12.pt
        fontWeight = FontWeight.w600
        color = TEXT_BLACK
    }
    val dropDownLabel by css {
        fontFamily = TextStyle.FONT_FAMILY_MAIN
        fontSize = 12.pt
        fontWeight = FontWeight.w200
        color = TEXT_BLACK
    }
    val optionStatusMessage by css {
        fontFamily = FONT_FAMILY_MAIN
        fontSize = 10.pt
        fontWeight = FontWeight.w600
    }
    val optionStatusFail by css {
        +optionStatusMessage
        color = HL7_RED
    }
    val optionStatusSuccess by css {
        +optionStatusMessage
        color = SUCCESS_GREEN
    }
}