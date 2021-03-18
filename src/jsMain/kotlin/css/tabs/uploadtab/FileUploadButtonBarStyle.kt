package css.tabs.uploadtab

import kotlinx.css.*
import styled.StyleSheet

object FileUploadButtonBarStyle : StyleSheet("FileUploadButtonBar", isStatic = true) {
    val buttonBarContainer by css {
        display = Display.inlineFlex
        flexDirection = FlexDirection.row
        alignItems = Align.center
        padding(vertical = 16.px)
    }
}