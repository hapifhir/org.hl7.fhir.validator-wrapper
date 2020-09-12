package css

import css.const.WHITE
import kotlinx.css.*
import kotlinx.css.properties.boxShadow
import styled.StyleSheet

object HeaderStyle : StyleSheet("HeaderStyle", isStatic = true) {

    val HEADER_HEIGHT = 80.px

    val headerBar by css {
        display = Display.flex
        width = 100.pct
        height = 5.rem
        zIndex = 1
        top = 0.px
        position = Position.fixed
        backgroundColor = WHITE
        flexDirection = FlexDirection.row
    }

    val header by css {
        padding(horizontal = 2.rem)
        flex(flexBasis = 100.pct)
        display = Display.flex
        flexDirection = FlexDirection.row
    }

    val headerMainImage by css {
        height = HEADER_HEIGHT * 0.6
        alignSelf = Align.center
    }

    val headerMenu by css {
        width = 100.pct
        height = HEADER_HEIGHT * 0.8
        display = Display.flex
        flexDirection = FlexDirection.row
        padding(horizontal = 1.rem)
        alignSelf = Align.center
    }

    val headerBarScrolled by css {
        boxShadow(color = Color("#BBBBBB"), offsetX = 0.px, offsetY = 10.px, blurRadius = 10.px)
    }

    val menuEntriesContainer by css {
        display = Display.flex
        flex(flexGrow = 1.0)
        flexDirection = FlexDirection.row
        justifyContent = JustifyContent.flexStart
        alignSelf = Align.center
    }

    val menuEntries by css {
        padding(horizontal = 1.rem)
    }

    val sideOptions by css {
        display = Display.flex
        flexDirection = FlexDirection.row
        alignSelf = Align.center
    }
}