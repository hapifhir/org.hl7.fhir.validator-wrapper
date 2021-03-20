package css.component.tabs.uploadtab.filelist

import kotlinx.css.*
import styled.StyleSheet

object FileEntryStyle : StyleSheet("FileItemStyle") {
    val fileEntryContainer by css {
        display = Display.flex
        height = 96.px
        flex(flexBasis = 100.pct)
        padding(horizontal = 32.px)
        flexDirection = FlexDirection.row
        justifyContent = JustifyContent.flexStart
        alignItems = Align.center
    }

    val titleField by css {
        flexGrow = 1.0
        paddingLeft = 16.px
    }
}