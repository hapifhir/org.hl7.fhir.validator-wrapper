package css.component

import kotlinx.css.Display
import kotlinx.css.display
import kotlinx.css.paddingTop
import kotlinx.css.px
import styled.StyleSheet
import ui.components.header.HeaderStyle

object LandingPageStyle : StyleSheet("LandingPage", isStatic = true) {
    val mainDiv by css {
        paddingTop = HeaderStyle.HEADER_HEIGHT
        display = Display.flex
    }
}