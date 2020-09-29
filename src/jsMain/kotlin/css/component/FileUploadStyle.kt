package css.component

import kotlinx.css.*
import styled.StyleSheet

object FileUploadStyle : StyleSheet("FileUploadStyle", isStatic = true) {

    val layout by FileUploadStyle.css {
        position = Position.relative
        display = Display.flex
        flex(flexBasis = 100.pct)
    }

    val buttonContainer by FileUploadStyle.css {
        display = Display.flex
        flexDirection = FlexDirection.column
        justifyContent = JustifyContent.center
    }
}