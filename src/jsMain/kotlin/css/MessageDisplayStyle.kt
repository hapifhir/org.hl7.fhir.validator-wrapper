package css

import css.FileItemStyle.fadeIn
import css.const.*
import kotlinx.css.*
import kotlinx.css.properties.border
import kotlinx.css.properties.ms
import styled.StyleSheet
import styled.animation

object MessageDisplayStyle : StyleSheet("FileSummary", isStatic = true) {

    val toolTipContainer by css {

    }

    val toolTipOutput by css {
        padding(0.px)
        margin(0.px)
    }
}