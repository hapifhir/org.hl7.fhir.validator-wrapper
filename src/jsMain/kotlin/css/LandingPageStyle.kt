package css

import kotlinx.css.*
import kotlinx.css.properties.border
import kotlinx.css.properties.s
import styled.StyleSheet
import styled.animation
import kotlin.Float

object LandingPageStyle : StyleSheet("LandingPage", isStatic = true) {
    val mainDiv by css {
        paddingTop = HeaderStyle.HEADER_HEIGHT
        padding(horizontal = 0.px)
        margin(0.px)
    }
}