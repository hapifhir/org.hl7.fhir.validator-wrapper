package css.component.fileupload.filelist

import css.const.BORDER_GRAY
import css.const.GRAY_300
import css.const.PADDING_S
import kotlinx.css.*
import kotlinx.css.properties.border
import kotlinx.css.properties.borderBottom
import kotlinx.css.properties.boxShadow
import styled.StyleSheet

object FileEntryListStyle : StyleSheet("FileEntryListStyle", isStatic = true) {

    val entryListContainer by FileEntryListStyle.css {
        display = Display.flex
        flex(flexBasis = 100.pct)
        width = 100.pct
        height = 100.pct
        backgroundColor = Color.white
        border(width = 1.px, color = BORDER_GRAY, style = BorderStyle.solid)
    }

    val entryList by FileEntryListStyle.css {
        flex(flexBasis = 100.pct)
        padding(0.px)
        margin(0.px)
        listStyleType = ListStyleType.none
    }

    val listSeparator by FileEntryListStyle.css {
        borderBottom(width = 1.px, color = BORDER_GRAY, style = BorderStyle.solid)
    }
}