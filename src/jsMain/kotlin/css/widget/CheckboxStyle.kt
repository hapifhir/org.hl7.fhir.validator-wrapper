package css.widget

import css.const.GRAY_200
import css.const.ICON_SMALL_DIM
import kotlinx.css.*
import styled.StyleSheet

object CheckboxStyle : StyleSheet("CheckboxStyle", isStatic = true) {

    val propertiesDetails by css {
        overflow = Overflow.hidden
        backgroundColor = GRAY_200
    }

    val dropdownButtonContainer by css {
        display = Display.flex
        flex(flexGrow = 1.0)
        flexDirection = FlexDirection.row
        justifyContent = JustifyContent.flexEnd
    }

    val dropdownButton by css {
        width = ICON_SMALL_DIM
        height = ICON_SMALL_DIM
        alignSelf = Align.center
    }

    val checkboxTitleBar by css {
        display = Display.flex
        flex(flexGrow = 1.0)
        flexDirection = FlexDirection.row
        alignSelf = Align.center
        padding(1.rem)
    }

    val checkboxTitle by css {
        flexGrow = 1.0
        width = 100.pct
        display = Display.flex
        flexDirection = FlexDirection.row
        justifyContent = JustifyContent.flexStart
    }
}