package css.component

import css.text.TextStyle
import css.const.GRAY_200
import css.const.SHADOW
import kotlinx.css.*
import kotlinx.css.properties.boxShadow
import styled.StyleSheet

object MessageDisplayStyle : StyleSheet("MessageDisplayStyle", isStatic = true) {

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