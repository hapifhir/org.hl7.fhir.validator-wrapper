package css.component.buttons

import kotlinx.css.*
import styled.StyleSheet

object TextButtonStyle : StyleSheet("GenericButtonStyle", isStatic = true) {

    val button by TextButtonStyle.css {
        cursor = Cursor.pointer
        display = Display.flex
        alignItems = Align.flexStart
        flexDirection = FlexDirection.row
        padding(horizontal = 16.px, vertical = 8.px)
    }
}