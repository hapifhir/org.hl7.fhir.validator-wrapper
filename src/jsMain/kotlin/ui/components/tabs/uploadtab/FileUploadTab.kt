package ui.components.tabs.uploadtab

import css.component.tabs.TabStyle
import kotlinx.css.Align
import kotlinx.css.Display
import kotlinx.css.alignItems
import kotlinx.css.display
import model.FileInfo
import model.ValidationOutcome
import react.*
import styled.css
import styled.styledDiv
import ui.components.tabs.tabHeading
import ui.components.tabs.uploadtab.filelist.fileEntryList

external interface FileUploadTabProps : RProps {
    var uploadedFiles: List<ValidationOutcome>
    var deleteFile: (FileInfo) -> Unit
}

/**
 * Component displaying the list of uploaded files, in addition to the controls for uploading and starting validation.
 */
class FileUploadTab : RComponent<FileUploadTabProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                // If the tab is currently displayed on screen, we define a layout type, otherwise, we set to none
                display = Display.flex
                alignItems = Align.flexStart
                +TabStyle.tabContent
            }

            tabHeading {
                text = "Uploaded Files (${props.uploadedFiles.size})"
            }
            fileEntryList {
                validationOutcomes = props.uploadedFiles
                viewFile = {
                    println("view file ${it.getFileInfo().fileName}")
                }
                deleteFile = {
                    println("delete file ${it.getFileInfo().fileName}")
                }
            }
            fileUploadButtonBar {
                onUploadRequested = {
                    println ("Handle Upload")
                }
                onValidateRequested = {
                    println ("Handle Validate")
                }
            }
        }
    }
}

fun RBuilder.fileUploadTab(handler: FileUploadTabProps.() -> Unit): ReactElement {
    return child(FileUploadTab::class) {
        this.attrs(handler)
    }
}