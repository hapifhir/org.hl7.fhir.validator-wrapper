package css.component

import css.const.*
import css.text.TextStyle
import css.component.tabs.uploadtab.filelist.FileStatusSpinnerStyle
import kotlinx.css.*
import kotlinx.css.properties.boxShadow
import styled.StyleSheet

object OptionEntryFieldStyle : StyleSheet("OptionEntryFieldStyle", isStatic = true) {

    val mainDiv by css {
        display = Display.flex
        flexDirection = FlexDirection.column
    }
    val textAreaAndButtonDiv by css {
        display = Display.flex
        flexDirection = FlexDirection.row
    }
    val textArea by css {
        flex(flexBasis = 50.pct)
        alignSelf = Align.center
        padding(0.5.rem)
        +TextStyle.codeDark
    }
    val buttonDiv by css {
        display = Display.flex
        flexGrow = 1.0
        justifyContent = JustifyContent.flexEnd
    }
    val loadingIndicator by css {
        +FileStatusSpinnerStyle.loadingIndicator
        width = ICON_SMALL_DIM
        height = ICON_SMALL_DIM
        alignSelf = Align.center
        padding(0.5.rem)
    }
    val submitButton by css {
        width = ICON_SMALL_DIM
        height = ICON_SMALL_DIM
        alignSelf = Align.center
        padding(0.5.rem)
    }
    val errorMessage by css {
        display = Display.flex
        justifyContent = JustifyContent.center
        backgroundColor = GRAY_100
        padding(0.5.rem)
        margin(vertical = 1.rem, horizontal = 0.5.rem)
        boxShadow(color = SHADOW, offsetX = 0.px, offsetY = 5.px, blurRadius = 5.px)
        +TextStyle.codeError
    }
}