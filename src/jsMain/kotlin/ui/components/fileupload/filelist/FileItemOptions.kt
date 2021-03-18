package ui.components.fileupload.filelist

import css.const.HL7_RED
import css.const.SUCCESS_GREEN
import css.const.WHITE
import kotlinx.css.*
import react.*
import styled.css
import styled.styledDiv
import ui.components.buttons.textButton

external interface FileUploadButtonBarProps : RProps {
    var viewOption: Boolean
}

/**
 * Component displaying the horizontal list of buttons for file upload and validation
 */
class FileUploadButtonBar : RComponent<FileUploadButtonBarProps, RState>() {

    override fun RBuilder.render() {
        styledDiv {
            css {
                display = Display.flex
                flexDirection = FlexDirection.row
                alignItems = Align.flexStart
            }

            textButton {
                textColor = SUCCESS_GREEN
                active = props.viewOption
                label = "View"
                onSelected = {
                    println("View button selected!")
                }
            }

            styledDiv {
                css {
                    width = 16.px
                }
            }

            textButton {
                textColor = HL7_RED
                active = true
                label = "Delete"
                onSelected = {
                    println("Delete button selected!")
                }
            }
        }
    }
}

fun RBuilder.fileItemOptions(handler: FileUploadButtonBarProps.() -> Unit): ReactElement {
    return child(FileUploadButtonBar::class) {
        this.attrs(handler)
    }
}