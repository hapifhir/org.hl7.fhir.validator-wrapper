package ui.components.tabs.uploadtab

import css.tabs.TabBarStyle
import css.tabs.TabStyle
import kotlinx.css.Align
import kotlinx.css.Display
import kotlinx.css.alignItems
import kotlinx.css.display
import model.FileInfo
import model.IssueSeverity
import model.ValidationMessage
import model.ValidationOutcome
import react.*
import styled.css
import styled.styledDiv
import styled.styledSpan
import ui.components.tabs.tabHeading
import ui.components.tabs.uploadtab.filelist.fileEntryList

external interface FileUploadTabProps : RProps {
    var uploadedFiles: List<ValidationOutcome>
    var deleteFile: (FileInfo) -> Unit

    var active: Boolean
}

/**
 * Component displaying the list of uploaded files, in addition to the controls for uploading and starting validation.
 */
class FileUploadTab : RComponent<FileUploadTabProps, RState>() {
    val results = emptyList<ValidationOutcome>()
    val temp = listOf<ValidationOutcome>(
        ValidationOutcome()
            .setFileInfo(FileInfo().setFileName("test_file1.json"))
            .setValidated(true)
            .setValidating(false)
            .setMessages(listOf(ValidationMessage().setLevel(level = IssueSeverity.FATAL))),
        ValidationOutcome()
            .setFileInfo(FileInfo().setFileName("test_file2.json"))
            .setValidated(true)
            .setValidating(false)
            .setMessages(listOf(ValidationMessage().setLevel(level = IssueSeverity.ERROR))),
        ValidationOutcome()
            .setFileInfo(FileInfo().setFileName("test_file3.json"))
            .setValidated(true)
            .setValidating(false)
            .setMessages(listOf(ValidationMessage().setLevel(level = IssueSeverity.WARNING))),
        ValidationOutcome()
            .setFileInfo(FileInfo().setFileName("test_file4.json"))
            .setValidated(true)
            .setValidating(false)
            .setMessages(listOf(ValidationMessage().setLevel(level = IssueSeverity.INFORMATION))),
        ValidationOutcome()
            .setFileInfo(FileInfo().setFileName("test_file5.json"))
            .setValidated(false)
            .setValidating(true)
            .setMessages(listOf(ValidationMessage().setLevel(level = IssueSeverity.FATAL))),
        ValidationOutcome()
            .setFileInfo(FileInfo().setFileName("test_file6.json"))
            .setValidated(false)
            .setValidating(false)
            .setMessages(listOf(ValidationMessage().setLevel(level = IssueSeverity.FATAL)))
    )

    override fun RBuilder.render() {
        styledDiv {
            css {
                // If the tab is currently displayed on screen, we define a layout type, otherwise, we set to none
                display = if (props.active) Display.flex else Display.none
                alignItems = Align.flexStart
                +TabStyle.tabContent
            }

            tabHeading {
                text = "Uploaded Files"
            }
            fileEntryList {
                validationOutcomes = results
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