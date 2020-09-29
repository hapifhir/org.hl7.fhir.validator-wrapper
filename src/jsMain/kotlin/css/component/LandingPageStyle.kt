package css.component

import css.component.HeaderStyle
import kotlinx.css.*
import styled.StyleSheet

object LandingPageStyle : StyleSheet("LandingPage", isStatic = true) {
    val mainDiv by css {
        paddingTop = HeaderStyle.HEADER_HEIGHT
        display = Display.flex
    }
}