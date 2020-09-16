package css

import css.const.*
import kotlinx.css.*
import kotlinx.css.properties.border
import kotlinx.css.properties.boxShadow
import styled.StyleSheet

object ContextSettingsStyle : StyleSheet("ContextSettingsStyle", isStatic = true) {

    val mainDiv by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        padding(1.rem)
        margin(horizontal = 2.rem)
        border(width = 1.px, style = BorderStyle.solid, color = GRAY_300, borderRadius = 4.px)
    }

}