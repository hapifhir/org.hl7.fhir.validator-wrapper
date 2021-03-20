package css.component.validation.issueslist

import css.const.BORDER_GRAY
import css.const.WHITE
import kotlinx.css.*
import kotlinx.css.properties.borderBottom
import kotlinx.css.properties.borderRight
import kotlinx.css.properties.borderTop
import styled.StyleSheet

object IssueEntryStyle : StyleSheet("IssueEntryStyle", isStatic = true) {

    val issueContainer by css {
        display = Display.inlineFlex
        flexDirection = FlexDirection.row
        minHeight = 64.px
        width = 100.pct
        backgroundColor = WHITE
        borderTop(width = 1.px, style = BorderStyle.solid, color = BORDER_GRAY)
        borderRight(width = 1.px, style = BorderStyle.solid, color = BORDER_GRAY)
        borderBottom(width = 1.px, style = BorderStyle.solid, color = BORDER_GRAY)
        padding(16.px)
        boxSizing = BoxSizing.borderBox
    }

    val levelAndLineNumber by css {
        paddingRight = 16.px
        minWidth = 20.pct
        alignSelf = Align.center
    }

    val messageDetails by css {
        flex(flexBasis = 100.pct)
        alignSelf = Align.center
        overflowWrap = OverflowWrap.breakWord
    }
}