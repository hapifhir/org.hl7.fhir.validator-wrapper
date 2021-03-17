package ui.components.fileupload

import css.component.page.TabBarStyle
import css.const.HL7_RED
import css.const.WHITE
import kotlinx.css.*
import react.*
import styled.css
import styled.styledDiv
import ui.components.buttons.genericButton
import ui.components.fileUploadComponent

external interface FileUploadButtonBarProps : RProps {
    var active: Boolean
}

/**
 * Component displaying the horizontal list of buttons for file upload and validation
 */
class FileUploadButtonBar : RComponent<FileUploadButtonBarProps, RState>() {

    override fun RBuilder.render() {
        styledDiv {
            css {
                display = Display.flex
                flexDirection = FlexDirection.column
                gap = Gap("16.px")
            }

            genericButton {
                backgroundColor = WHITE
                borderColor = HL7_RED
                image = "images/upload_red.png"
                label = "Upload"
                onSelected = {
                    println("Upload button selected!")
                }
            }
        }
    }
}

fun RBuilder.fileUploadButtonBar(handler: FileUploadButtonBarProps.() -> Unit): ReactElement {
    return child(FileUploadButtonBar::class) {
        this.attrs(handler)
    }
}