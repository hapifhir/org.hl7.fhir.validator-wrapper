package css.component.tabs

import css.const.*
import kotlinx.css.*
import kotlinx.css.properties.*
import styled.StyleSheet

object TabBarStyle : StyleSheet("TabBar", isStatic = true) {

    val mainLayout by css {
        height = 600.px
        display = Display.inlineFlex
        flexDirection = FlexDirection.column
    }

    val tabBar by css {
        display = Display.flex
        justifyContent = JustifyContent.center
        flex(flexBasis = 64.px)
    }

    val tabButton by css {
        float = Float.left
        cursor = Cursor.pointer
        display = Display.inlineFlex
        alignItems = Align.center
        justifyContent = JustifyContent.center
        height = 64.px
        width = 256.px
        boxSizing = BoxSizing.borderBox
    }

    val tabButtonInactive by css {
        backgroundColor = INACTIVE_GRAY
        border(width = 1.px, style = BorderStyle.solid, color = BORDER_GRAY)
        +tabButton
    }

    val tabButtonActive by css {
        backgroundColor = WHITE
        borderLeft(width = 1.px, style = BorderStyle.solid, color = BORDER_GRAY)
        borderRight(width = 1.px, style = BorderStyle.solid, color = BORDER_GRAY)
        borderTop(width = 4.px, style = BorderStyle.solid, color = HL7_RED)
        +tabButton
    }

    val tabButtonHover by css {
        backgroundColor = HIGHLIGHT_GRAY
        +tabButton
    }

    val tabBodyContainer by css {
        flex(flexBasis = 100.pct)
        display = Display.flex
        flexDirection = FlexDirection.column
    }

    val tabFill by css {
        height = 100.pct
        boxSizing = BoxSizing.borderBox
        borderBottom(width = 1.px, style = BorderStyle.solid, color = BORDER_GRAY)
    }

    val leftRightTabFill by css {
        flexGrow = 1.0
        +tabFill
    }

    val dividerTabFill by css {
        width = 16.px
        +tabFill
    }
}