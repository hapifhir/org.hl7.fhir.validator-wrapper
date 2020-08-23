package css

import css.const.ICON_SMALL_DIM
import css.const.PADDING_S
import kotlinx.css.*
import styled.StyleSheet

object FileItemStyle : StyleSheet("FileInfo", isStatic = true) {
    val listItem by FileItemStyle.css {
        display = Display.flex
        margin(4.px)
        flex(flexBasis = 100.pct)
        flexDirection = FlexDirection.row
        justifyContent = JustifyContent.flexStart
        alignItems = Align.center
    }

    val typeImage by FileItemStyle.css {
        height = ICON_SMALL_DIM
        width = ICON_SMALL_DIM
        padding(PADDING_S)
    }

    val titleField by FileItemStyle.css {
        flexGrow = 1.0
        paddingLeft = PADDING_S
    }
}