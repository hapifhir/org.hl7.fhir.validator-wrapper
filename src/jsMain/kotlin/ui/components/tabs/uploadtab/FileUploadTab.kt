package ui.components.tabs.uploadtab

import css.animation.FadeIn.fadeIn
import css.const.WHITE
import kotlinx.css.*
import model.FileInfo
import model.ValidationOutcome
import react.*
import styled.StyleSheet
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
                +TabStyle.tabContent
            }
            tabHeading {
                text = "Uploaded Files (${props.uploadedFiles.size})"
            }
            fileEntryList {
                validationOutcomes = props.uploadedFiles
                viewFile = {
                    // TODO
                }
                deleteFile = {
                    // TODO
                }
            }
            fileUploadButtonBar {
                onUploadRequested = {
                    // TODO
                }
                onValidateRequested = {
                    // TODO
                }
            }
        }
    }
}

/**
 * React Component Builder
 */
fun RBuilder.fileUploadTab(handler: FileUploadTabProps.() -> Unit): ReactElement {
    return child(FileUploadTab::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object TabStyle : StyleSheet("TabStyle", isStatic = true) {
    val tabContent by css {
        backgroundColor = WHITE
        flexDirection = FlexDirection.column
        justifyContent = JustifyContent.flexStart
        alignItems = Align.flexStart
        display = Display.flex
        padding(horizontal = 32.px, vertical = 16.px)
        fadeIn()
        flex(flexBasis = 100.pct)
    }
}