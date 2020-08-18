package css

import kotlinx.css.*
import kotlinx.css.properties.*
import styled.StyleSheet
import styled.animation

object TabBarStyle : StyleSheet("TabBar", isStatic = true) {

    val mainLayout by css {
        height = 512.px
        display = Display.flex
        flexDirection = FlexDirection.column
    }

    val tabBar by css {
        overflow = Overflow.hidden
        display = Display.flex
        justifyContent = JustifyContent.center
        alignItems = Align.flexStart
        flex(flexBasis = 48.px)
    }

    val tabButton by css {
        float = Float.left
        outline = Outline.none
        overflow = Overflow.hidden
        borderStyle = BorderStyle.none
        cursor = Cursor.pointer
        padding(left = 14.px, top = 16.px, right = 14.px, bottom = 16.px)
        borderLeft(width = 1.px, style = BorderStyle.solid, color = NOT_BLACK)
        borderRight(width = 1.px, style = BorderStyle.solid, color = NOT_BLACK)
        margin(vertical = 0.px, horizontal = 4.px)
    }

    val tabButtonInactive by css {
        backgroundColor = Color("#f1f1f1")
        borderTop(width = 1.px, style = BorderStyle.solid, color = NOT_BLACK)
        borderBottom(width = 1.px, style = BorderStyle.solid, color = NOT_BLACK)
        +tabButton
    }

    val tabButtonActive by css {
        backgroundColor = Color("#ddd")
        borderTop(width = 4.px, style = BorderStyle.solid, color = HL7_RED)
        +tabButton
    }

    val tabButtonHover by css {
        backgroundColor = Color("#ddd")
        borderTop(width = 1.px, style = BorderStyle.solid, color = NOT_BLACK)
        borderBottom(width = 1.px, style = BorderStyle.solid, color = NOT_BLACK)
        +tabButton
    }

    val tabBodyContainer by css {
        flex(flexBasis = 100.pct)
        display = Display.flex
        flexDirection = FlexDirection.column
    }

    val body by css {
        backgroundColor = Color("#ddd")
        padding(24.px)
        fadeIn()
        flex(flexBasis = 100.pct)
    }

    fun CSSBuilder.fadeIn() {
        animation (duration = 1.s) {
            from { opacity = 0 }
            to {opacity = 1}
        }
    }
}