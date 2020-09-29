package ui.components

import css.component.TabBarStyle
import kotlinx.css.Display
import kotlinx.css.display
import react.*
import styled.css
import styled.styledDiv

external interface FileUploadTabProps : RProps {
    var active: Boolean
}

class FileUploadTab : RComponent<FileUploadTabProps, RState>() {

    override fun RBuilder.render() {
        styledDiv {
            css {
                display = if (props.active) Display.flex else Display.none
                +TabBarStyle.body
            }
            fileUploadComponent {

            }
        }
    }
}

fun RBuilder.fileUploadTab(handler: FileUploadTabProps.() -> Unit): ReactElement {
    return child(FileUploadTab::class) {
        this.attrs(handler)
    }
}