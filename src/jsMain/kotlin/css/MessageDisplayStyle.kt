package css

import css.FileItemStyle.fadeIn
import css.const.*
import kotlinx.css.*
import kotlinx.css.properties.border
import kotlinx.css.properties.boxShadow
import kotlinx.css.properties.ms
import styled.StyleSheet
import styled.animation

object MessageDisplayStyle : StyleSheet("FileSummary", isStatic = true) {

    val messageLayout by css {
        display = Display.inlineFlex
        flexDirection = FlexDirection.row
        minHeight = 64.px
        width = 100.pct
        backgroundColor = GRAY_200
        boxShadow(color = SHADOW, offsetX = 0.px, offsetY = 5.px, blurRadius = 5.px)
        marginBottom = 10.px
    }

    val levelAndLineNumber by css {
        padding(6.px)
        flex(flexBasis = FlexBasis.auto)
        alignSelf = Align.center
        +TextStyle.toolTipTextBold
    }

    val messageDetails by css {
        padding(6.px)
        flex(flexBasis = 100.pct)
        alignSelf = Align.center
        overflowWrap = OverflowWrap.breakWord
        +TextStyle.code
    }
}