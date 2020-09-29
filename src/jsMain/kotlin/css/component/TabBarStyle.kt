package css.component

import css.animation.FadeIn.fadeIn
import css.const.*
import kotlinx.css.*
import kotlinx.css.properties.*
import styled.StyleSheet

object TabBarStyle : StyleSheet("TabBar", isStatic = true) {

    val mainLayout by css {
        height = 600.px
        display = Display.flex
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
        display = Display.flex
        alignItems = Align.center
        justifyContent = JustifyContent.center
        height = 100.pct
        width = 320.px
        boxSizing = BoxSizing.borderBox
    }

    val tabButtonInactive by css {
        backgroundColor = WHITE
        border(width = 1.px, style = BorderStyle.solid, color = GRAY_600)
        +tabButton
    }

    val tabButtonActive by css {
        backgroundColor = GRAY_100
        borderLeft(width = 1.px, style = BorderStyle.solid, color = GRAY_600)
        borderRight(width = 1.px, style = BorderStyle.solid, color = GRAY_600)
        borderTop(width = 4.px, style = BorderStyle.solid, color = TRULY_RED)
        +tabButton
    }

    val tabButtonHover by css {
        backgroundColor = GRAY_200
        +tabButton
    }

    val tabBodyContainer by css {
        flex(flexBasis = 100.pct)
        display = Display.flex
        flexDirection = FlexDirection.column
    }

    val body by css {
        backgroundColor = GRAY_100
        padding(24.px)
        fadeIn()
        flex(flexBasis = 100.pct)
    }
}