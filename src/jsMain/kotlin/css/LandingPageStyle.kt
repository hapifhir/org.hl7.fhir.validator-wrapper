package css

import kotlinx.css.margin
import kotlinx.css.padding
import kotlinx.css.paddingTop
import kotlinx.css.px
import styled.StyleSheet

object LandingPageStyle : StyleSheet("LandingPage", isStatic = true) {
    val mainDiv by css {
        paddingTop = HeaderStyle.HEADER_HEIGHT
        padding(horizontal = 0.px)
        margin(0.px)
    }
}