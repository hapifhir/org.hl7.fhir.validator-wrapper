package ui.components.tabs.uploadtab.filelist

import css.const.HL7_RED
import css.const.SUCCESS_GREEN
import kotlinx.css.*
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import ui.components.buttons.textButton

external interface FileEntryOptionsProps : Props {
    var viewOption: Boolean
    var viewText: String
    var deleteText: String
    var onViewClicked: () -> Unit
    var onDeleteClicked: () -> Unit
}

/**
 * Component displaying the horizontal list of buttons for file upload and validation
 */
class FileEntryOptions : RComponent<FileEntryOptionsProps, State>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                +FileEntryOptionsStyle.optionsContainer
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
                    +FileEntryOptionsStyle.optionsDivider
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

/**
 * React Component Builder
 */
fun RBuilder.fileEntryOptions(handler: FileEntryOptionsProps.() -> Unit) {
    return child(FileEntryOptions::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object FileEntryOptionsStyle : StyleSheet("FileEntryOptionsStyle", isStatic = true) {
    val optionsContainer by FileEntryOptionsStyle.css {
        display = Display.flex
        flexDirection = FlexDirection.row
        alignItems = Align.flexStart
    }
    val optionsDivider by FileEntryOptionsStyle.css {
        width = 8.px
    }
}