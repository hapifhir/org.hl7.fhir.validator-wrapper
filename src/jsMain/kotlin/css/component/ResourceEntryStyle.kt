package css.component

import kotlinx.css.*
import styled.StyleSheet

object ResourceEntryStyle : StyleSheet("ResourceEntryStyle", isStatic = true) {

    val entryContainer by css {
        position = Position.relative
        display = Display.flex
        flex(flexBasis = 100.pct)
    }
    val entryField by css {
        overflow = Overflow.auto
        wordWrap = WordWrap.unset
        resize = Resize.none
        flex(flexBasis = 100.pct)
        padding(1.rem)
    }

}