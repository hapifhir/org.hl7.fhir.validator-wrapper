package css

import kotlinx.css.*
import kotlinx.css.properties.*
import styled.StyleSheet
import styled.animation
import kotlin.time.seconds

object HeaderStyle : StyleSheet("HeaderStyle", isStatic = true) {

    val HEADER_HEIGHT = 80.px
    val HEADER_WIDTH = 100.pct
    val HEADER_PADDING = HEADER_HEIGHT * 0.1
    val HEADER_BACKGROUND = Color("#ffffff")

    val headerBarTop by css {
        width = HEADER_WIDTH
        height = HEADER_HEIGHT
        position = Position.fixed
        top = 0.px
        backgroundColor = HEADER_BACKGROUND
        paddingLeft = HEADER_PADDING
        paddingRight = HEADER_PADDING
        display = Display.flex
        alignItems = Align.center
    }

    val headerBarScrolled by css {
        width = HEADER_WIDTH
        height = HEADER_HEIGHT
        position = Position.fixed
        top = 0.px
        backgroundColor = HEADER_BACKGROUND
        paddingLeft = HEADER_PADDING
        paddingRight = HEADER_PADDING
        display = Display.flex
        alignItems = Align.center
        boxShadow(color = Color("#BBBBBB"), offsetX = 0.px, offsetY = 10.px, blurRadius = 10.px)
    }

}