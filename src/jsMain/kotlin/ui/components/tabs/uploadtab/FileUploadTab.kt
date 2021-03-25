package ui.components.tabs.uploadtab

import css.animation.FadeIn.fadeIn
import css.const.WHITE
import kotlinx.browser.document
import kotlinx.css.*
import model.FileInfo
import model.ValidationOutcome
import org.w3c.dom.HTMLInputElement
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import ui.components.tabs.tabHeading
import ui.components.tabs.uploadtab.filelist.fileEntryList
import ui.components.validation.validationSummaryPopup

external interface FileUploadTabProps : RProps {
    var uploadedFiles: List<ValidationOutcome>

    var deleteFile: (FileInfo) -> Unit
    var uploadFile: (FileInfo) -> Unit
}

class FileUploadTabState : RState {
    var currentlyDisplayedValidationOutcome: ValidationOutcome? = null
}

/**
 * Component displaying the list of uploaded files, in addition to the controls for uploading and starting validation.
 */
class FileUploadTab : RComponent<FileUploadTabProps, FileUploadTabState>() {

    init {
        state = FileUploadTabState()
    }
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
                viewFile = { outcomeToView ->
                    setState {
                        currentlyDisplayedValidationOutcome = outcomeToView
                    }
                }
                deleteFile = {
                    props.deleteFile(it.getFileInfo())
                }
            }
            fileUploadButtonBar {
                onUploadRequested = {
                    (document.getElementById(FILE_UPLOAD_ELEMENT_ID) as HTMLInputElement).click()
                }
                onValidateRequested = {
                    // TODO
                }
            }
            uploadFilesComponent {
                onFileUpload = {
                    props.uploadFile(it)
                }
            }
            state.currentlyDisplayedValidationOutcome?.let {
                validationSummaryPopup {
                    validationOutcome = state.currentlyDisplayedValidationOutcome!!
                    onClose = {
                        setState {
                            currentlyDisplayedValidationOutcome = null
                        }
                    }
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