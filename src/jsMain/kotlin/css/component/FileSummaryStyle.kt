package css.component

import css.animation.FadeIn.quickFadeIn
import css.const.*
import kotlinx.css.*
import kotlinx.css.properties.border
import styled.StyleSheet

object FileSummaryStyle : StyleSheet("FileSummary", isStatic = true) {

    val MODAL_MARGIN = 64.px
    val MODAL_BORDER_RADIUS = 8.px
    val BORDER_WIDTH = 1.px
    val TITLE_BAR_PADDING = 4.px
    val TITLE_BAR_HEIGHT = PADDING_XL

    val overlay by css {
        position = Position.fixed
        zIndex = 2
        left = 0.px
        top = 0.px
        right = 0.px
        bottom = 0.px
        backgroundColor = SHADOW
        quickFadeIn()
    }

    val modalContent by css {
        margin = "auto"
        marginTop = MODAL_MARGIN
        padding(PADDING_M)
        backgroundColor = GRAY_100
        borderRadius = MODAL_BORDER_RADIUS
        width = 70.pct
        minHeight = 50.pct
        maxHeight = 70.pct
        display = Display.flex
        flexDirection = FlexDirection.column
    }

    val titleBar by css {
        padding(TITLE_BAR_PADDING)
        height = TITLE_BAR_HEIGHT
        width = 100.pct
        display = Display.flex
        justifyContent = JustifyContent.right
    }

    val button by css {
        width = ICON_SMALL_DIM
        height = ICON_SMALL_DIM
        alignSelf = Align.center
        paddingLeft = 4.px
    }

    val filename by css {
        width = 100.pct
        alignSelf = Align.center
    }

    val horizontalRule by css {
        border(width = BORDER_WIDTH, style = BorderStyle.solid, color = GRAY_500)
        width = 100.pct
    }

    val fileContent by css {
        width = 100.pct
        flexGrow = 1.0
        display = Display.flex
        overflow = Overflow.scroll
        whiteSpace = WhiteSpace.preWrap
    }

}