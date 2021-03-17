package ui.components.fileupload

import css.component.page.TabBarStyle
import kotlinx.css.Display
import kotlinx.css.display
import react.*
import styled.css
import styled.styledDiv
import ui.components.fileUploadComponent

external interface FileUploadTabProps : RProps {
    var active: Boolean
}

/**
 * Component displaying the list of uploaded files, in addition to the controls for uploading and starting validation.
 */
class FileUploadTab : RComponent<FileUploadTabProps, RState>() {

    override fun RBuilder.render() {
        styledDiv {
            css {
                // If the tab is currently displayed on screen, we define a layout type, otherwise, we set to none
                display = if (props.active) Display.flex else Display.none
                +TabBarStyle.body
            }
//            fileUploadComponent {}
            fileUploadButtonBar { }
        }
    }
}

fun RBuilder.fileUploadTab(handler: FileUploadTabProps.() -> Unit): ReactElement {
    return child(FileUploadTab::class) {
        this.attrs(handler)
    }
}