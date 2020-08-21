package css

import kotlinx.css.*
import styled.StyleSheet

object ManualEntryStyle : StyleSheet("ManualEntry", isStatic = true) {

    val entryContainer by ManualEntryStyle.css {
        position = Position.relative
        display = Display.flex
        flex(flexBasis = 100.pct)
    }
    val entryField by ManualEntryStyle.css {
        overflow = Overflow.auto
        wordWrap = WordWrap.unset
        resize = Resize.none
        flex(flexBasis = 100.pct)
        padding(12.px)
    }
}