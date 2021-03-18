package ui.components.fileupload.filelist

import css.const.HL7_RED
import css.const.SUCCESS_GREEN
import kotlinx.css.*
import react.*
import styled.css
import styled.styledDiv
import ui.components.buttons.textButton

external interface FileEntryOptionsProps : RProps {
    var viewOption: Boolean
    var onViewClicked: () -> Unit
    var onDeleteClicked: () -> Unit
}

/**
 * Component displaying the horizontal list of buttons for file upload and validation
 */
class FileEntryOptions : RComponent<FileEntryOptionsProps, RState>() {

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
                    props.onViewClicked()
                }
            }

            styledDiv {
                css {
                    width = 8.px
                }
            }

            textButton {
                textColor = HL7_RED
                active = true
                label = "Delete"
                onSelected = {
                    props.onDeleteClicked()
                }
            }
        }
    }
}

fun RBuilder.fileEntryOptions(handler: FileEntryOptionsProps.() -> Unit): ReactElement {
    return child(FileEntryOptions::class) {
        this.attrs(handler)
    }
}