package css.component.validation.issueslist

import css.component.tabs.uploadtab.filelist.FileEntryListStyle
import css.const.BORDER_GRAY
import kotlinx.css.*
import kotlinx.css.properties.border
import kotlinx.css.properties.borderBottom
import styled.StyleSheet

object IssueEntryListStyle : StyleSheet("IssueEntryListStyle", isStatic = true) {

    val entryListContainer by IssueEntryListStyle.css {
        display = Display.flex
        flex(flexBasis = 100.pct)
        padding(8.px)
        width = 100.pct
        height = 100.pct
        backgroundColor = Color.white
    }

    val entryList by IssueEntryListStyle.css {
        flex(flexBasis = 100.pct)
        padding(0.px)
        margin(0.px)
    }

    val listSeparator by IssueEntryListStyle.css {
        marginTop = 8.px
    }
}