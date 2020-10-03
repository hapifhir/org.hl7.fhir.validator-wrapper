package css.component

import css.const.*
import css.text.TextStyle
import kotlinx.css.*
import kotlinx.css.properties.border
import kotlinx.css.properties.boxShadow
import styled.StyleSheet

object HeadingWithDropDownExplanationStyle : StyleSheet("HeadingWithDropDownExplanationStyle", isStatic = true) {

    val mainDiv by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        margin(vertical = 1.rem)
    }

    val titleAndArrowDiv by css {
        display = Display.flex
        flexDirection = FlexDirection.row
    }

    val titleHeading by css {
        flexGrow = 1.0
        alignSelf = Align.center
        +TextStyle.h4
    }

    val arrowIcon by css {
        width = ICON_SMALL_DIM
        height = ICON_SMALL_DIM
        alignSelf = Align.center
    }

    val explanationBlock by css {
        overflow = Overflow.hidden
        backgroundColor = GRAY_200
        +TextStyle.codeDark
        padding(1.rem)
        marginTop = 0.5.rem
    }
}