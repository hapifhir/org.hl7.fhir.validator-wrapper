package css

import kotlinx.css.*
import styled.StyleSheet

object FileUploadStyle : StyleSheet("FileUpload", isStatic = true) {

    val layout by FileUploadStyle.css {
        position = Position.relative
        display = Display.flex
        flex(flexBasis = 100.pct)
    }

    val buttonContainer by FileUploadStyle.css {
        display = Display.flex
        flexDirection = FlexDirection.column
        position = Position.absolute
        right = 0.px
        bottom = 0.px
        margin(24.px)
    }


}