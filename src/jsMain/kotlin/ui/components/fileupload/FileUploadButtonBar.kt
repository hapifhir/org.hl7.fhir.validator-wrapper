package ui.components.fileupload

import css.const.HL7_RED
import css.const.WHITE
import kotlinx.css.*
import react.*
import styled.css
import styled.styledDiv
import ui.components.buttons.genericButton

external interface FileUploadButtonBarProps : RProps {}

/**
 * Component displaying the horizontal list of buttons for file upload and validation
 */
class FileUploadButtonBar : RComponent<FileUploadButtonBarProps, RState>() {

    override fun RBuilder.render() {
        styledDiv {
            css {
                display = Display.inlineFlex
                flexDirection = FlexDirection.row
                alignItems = Align.center
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

            styledDiv {
                css {
                    width = 16.px
                }
            }

            genericButton {
                backgroundColor = WHITE
                borderColor = HL7_RED
                image = "images/validate_red.png"
                label = "Validate"
                onSelected = {
                    println("Validate button selected!")
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