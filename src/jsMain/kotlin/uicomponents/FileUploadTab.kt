package uicomponents

import css.TabBarStyle
import kotlinx.css.*
import react.*
import styled.StyleSheet
import styled.*

/**
 * 
 */
external interface FileUploadTabProps: RProps {
    var active: Boolean
}

class FileUploadTab: RComponent<FileUploadTabProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                display = if (props.active) Display.flex else Display.none
                +TabBarStyle.body
                flex(flexBasis = 100.pct)
            }
            fileUploadComponent {  }

        }
    }
}

/**
 */
fun RBuilder.fileUploadTab(handler: FileUploadTabProps.() -> Unit): ReactElement {
    return child(FileUploadTab::class) {
        this.attrs(handler)
    }
}