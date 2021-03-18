package ui.components.tabs.uploadtab

import css.const.HL7_RED
import css.const.WHITE
import css.tabs.uploadtab.FileUploadButtonBarStyle
import kotlinx.css.px
import kotlinx.css.width
import react.*
import styled.css
import styled.styledDiv
import ui.components.buttons.genericButton

external interface FileUploadButtonBarProps : RProps {
    var onUploadRequested: () -> Unit
    var onValidateRequested: () -> Unit
}

/**
 * Component displaying the horizontal list of buttons for file upload and validation
 */
class FileUploadButtonBar : RComponent<FileUploadButtonBarProps, RState>() {

    override fun RBuilder.render() {
        styledDiv {
            css {
                +FileUploadButtonBarStyle.buttonBarContainer
            }

            genericButton {
                backgroundColor = WHITE
                borderColor = HL7_RED
                image = "images/upload_red.png"
                label = "Upload"
                onSelected = {
                    props.onUploadRequested()
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
                    props.onValidateRequested()
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