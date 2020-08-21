package css

import kotlinx.css.*
import kotlinx.css.properties.boxShadow
import styled.StyleSheet

object HeaderStyle : StyleSheet("HeaderStyle", isStatic = true) {

    val HEADER_HEIGHT = 80.px
    val HEADER_WIDTH = 100.pct
    val HEADER_PADDING = HEADER_HEIGHT * 0.1
    val HEADER_BACKGROUND = Color("#ffffff")

    val header by css {
        width = HEADER_WIDTH
        height = HEADER_HEIGHT
        position = Position.fixed
        zIndex = 1
        top = 0.px
        backgroundColor = HEADER_BACKGROUND
        paddingLeft = HEADER_PADDING
        paddingRight = HEADER_PADDING
        display = Display.flex
        alignItems = Align.center
    }

    val headerMainImage by css {
        height = HEADER_HEIGHT * 0.6
    }

    val headerFiller by css {
        width = 100.pct
        height = HEADER_HEIGHT * 0.8
    }

    val headerBarScrolled by css {
        boxShadow(color = Color("#BBBBBB"), offsetX = 0.px, offsetY = 10.px, blurRadius = 10.px)
    }

}