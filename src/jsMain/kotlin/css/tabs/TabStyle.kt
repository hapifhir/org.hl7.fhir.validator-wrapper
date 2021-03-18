package css.tabs

import css.animation.FadeIn.fadeIn
import css.const.WHITE
import kotlinx.css.*
import styled.StyleSheet

object TabStyle : StyleSheet("TabStyle", isStatic = true) {
    val headingContainer by TabStyle.css {
        display = Display.flex
        padding(vertical = 16.px)
        alignItems = Align.center
    }

    val tabContent by css {
        backgroundColor = WHITE
        flexDirection = FlexDirection.column
        justifyContent = JustifyContent.flexStart
        alignItems = Align.flexStart
        padding(horizontal = 32.px, vertical = 16.px)
        fadeIn()
        flex(flexBasis = 100.pct)
    }
}