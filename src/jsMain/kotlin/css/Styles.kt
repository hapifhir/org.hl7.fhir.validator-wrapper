package css

import kotlinx.css.*
import kotlinx.css.properties.*
import styled.StyleSheet
import styled.animation
import kotlin.time.seconds

object TabStyle : StyleSheet("Tabs", isStatic = true) {

    val tabContainer by css {
        overflow = Overflow.hidden
        border(width = 1.px, style = BorderStyle.solid, color = Color("#ccc"))
    }
    val tabButton by css {
        float = Float.left
        outline = Outline.none
        overflow = Overflow.hidden
        borderStyle = BorderStyle.none
        backgroundColor = Color("#f1f1f1")
        cursor = Cursor.pointer
        padding(left = 14.px, top = 16.px, right = 14.px, bottom = 16.px)
    }
    val tabButtonActive by css {
        float = Float.left
        outline = Outline.none
        overflow = Overflow.hidden
        borderStyle = BorderStyle.none
        backgroundColor = Color("#ddd")
        cursor = Cursor.pointer
        padding(left = 14.px, top = 16.px, right = 14.px, bottom = 16.px)
    }
    val tabButtonHover by css {
        backgroundColor = Color("#ddd")
    }
    val body by css {
        padding(left = 6.px, top = 12.px, right = 6.px, bottom = 12.px)
        border(width = 1.px, style = BorderStyle.solid, color = Color("#ccc"))
        borderTopStyle = BorderStyle.none
        fadeIn()
    }

    fun CSSBuilder.fadeIn() {
        animation (duration = 2.s) {
            from { opacity = 0 }
            to {opacity = 1}
        }
    }
}