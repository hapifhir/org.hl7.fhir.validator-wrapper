package css.component.buttons

import kotlinx.css.*
import styled.StyleSheet

object GenericButtonStyle : StyleSheet("GenericButtonStyle", isStatic = true) {

    val button by GenericButtonStyle.css {
        cursor = Cursor.pointer
        display = Display.inlineFlex
        flexDirection = FlexDirection.row
        minHeight = 32.px
        alignSelf = Align.center
        padding(horizontal = 16.px, vertical = 8.px)
    }

    val buttonImage by GenericButtonStyle.css {
        height = 16.px
        width = 16.px
        alignSelf = Align.center
    }
}