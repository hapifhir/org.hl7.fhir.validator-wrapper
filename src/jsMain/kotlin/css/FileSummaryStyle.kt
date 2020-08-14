package css

import kotlinx.css.*
import kotlinx.css.FlexBasis.Companion.fill
import kotlinx.css.properties.*
import kotlinx.html.MARK
import org.w3c.dom.css.CSSMarginRule
import styled.StyleSheet
import styled.animation

object FileSummaryStyle : StyleSheet("FileSummary", isStatic = true) {
    val MODAL_PADDING_TOP = 100.px
    val MODAL_BACKDROP = rgba(0,0,0,0.7)
    val MODAL_BACKGROUND = Color("#fefefe")
    val MODAL_PADDING = 20.px
    val MODAL_MARGIN = 64.px
    val MODAL_BORDER_RADIUS = 5.px
    val BORDER_WIDTH = 1.px
    val BORDER_COLOR = Color("#888")
    val CLOSE_BUTTON_COLOR = Color("#aaaaaa")
    val CLOSE_BUTTON_COLOR_HOVER = Color("#000")
    val BUTTON_SIZE = 32.px
    val TITLE_BAR_PADDING = 4.px
    val TITLE_BAR_HEIGHT = 48.px

    val overlay by FileSummaryStyle.css {
        position = Position.fixed
        left = 0.px
        top = 0.px
        right = 0.px
        bottom = 0.px
        backgroundColor = MODAL_BACKDROP
        quickFadeIn()
    }

    val modalContent by FileSummaryStyle.css {
        margin = "auto"
        marginTop = MODAL_MARGIN
        padding(MODAL_PADDING)
        backgroundColor = MODAL_BACKGROUND
        borderRadius = MODAL_BORDER_RADIUS
        width = 70.pct
        minHeight = 50.pct
        maxHeight = 70.pct
        position = Position.relative
        overflow = Overflow.hidden
        display = Display.flex
    }

    val titleBar by FileSummaryStyle.css {
        padding(TITLE_BAR_PADDING)
        height = TITLE_BAR_HEIGHT
        width = 100.pct
        position = Position.relative
    }

    val closeButton by FileSummaryStyle.css {
        color = CLOSE_BUTTON_COLOR
        float = Float.right
        width = BUTTON_SIZE
        height = BUTTON_SIZE
        position = Position.absolute
        right = 0.px
        +centerVertical
    }

    val closeButtonHover by FileSummaryStyle.css {
        color = CLOSE_BUTTON_COLOR_HOVER
        cursor = Cursor.pointer
    }

    val centerVertical by FileSummaryStyle.css {
        top = 50.pct
        transform {
            translateY((-50).pct)
        }
    }

    val horizontalRule by FileSummaryStyle.css {
        border(width = BORDER_WIDTH, style = BorderStyle.solid, color = HL7_RED)
    }

    val fileContent by FileSummaryStyle.css {
        backgroundColor = Color.green
        height = 100.pct
        width = 100.pct
        position = Position.absolute
        overflow = Overflow.scroll
        whiteSpace = WhiteSpace.nowrap
    }

    fun CSSBuilder.quickFadeIn() {
        animation (duration = 500.ms) {
            from { opacity = 0 }
            to {opacity = 1}
        }
    }
}