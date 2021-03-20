package css.component.buttons

import css.const.BORDER_GRAY
import css.const.HL7_RED
import css.const.INACTIVE_GRAY
import css.const.WHITE
import kotlinx.css.*
import kotlinx.css.properties.border
import styled.StyleSheet

object OptionButtonStyle : StyleSheet("OptionButtonStyle", isStatic = true) {
    val button by OptionButtonStyle.css {
        cursor = Cursor.pointer
        display = Display.inlineFlex
        minHeight = 24.px
        minWidth = 160.px
        alignSelf = Align.center
        justifyContent = JustifyContent.center
        alignItems = Align.center
        border(width = 1.px, style = BorderStyle.solid, color = BORDER_GRAY)
        borderRadius = 999.px
    }

    val buttonInactive by OptionButtonStyle.css {
        +button
        backgroundColor = INACTIVE_GRAY
        hover {
            backgroundColor = WHITE
        }
        active {
            backgroundColor = BORDER_GRAY
        }
    }

    val buttonActive by OptionButtonStyle.css {
        +button
        backgroundColor = WHITE
        hover {
            backgroundColor = INACTIVE_GRAY
        }
        active {
            backgroundColor = BORDER_GRAY
        }
    }
}