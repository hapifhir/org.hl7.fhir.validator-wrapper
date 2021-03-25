package ui.components.tabs.uploadtab

import api.sendValidationRequest
import css.animation.FadeIn.fadeIn
import css.const.WHITE
import kotlinx.browser.document
import kotlinx.coroutines.launch
import kotlinx.css.*
import mainScope
import model.CliContext
import model.FileInfo
import model.ValidationOutcome
import model.prettyPrint
import org.w3c.dom.HTMLInputElement
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import ui.components.tabs.tabHeading
import ui.components.tabs.uploadtab.filelist.fileEntryList
import ui.components.validation.validationSummaryPopup
import utils.assembleRequest

external interface FileUploadTabProps : RProps {
    var uploadedFiles: List<ValidationOutcome>
    var cliContext: CliContext
    var sessionId: String

    var deleteFile: (FileInfo) -> Unit
    var uploadFile: (FileInfo) -> Unit
    var setSessionId: (String) -> Unit
    var toggleValidationInProgress: (Boolean, FileInfo) -> Unit
    var addValidationOutcome: (ValidationOutcome) -> Unit
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
                    validateUploadedFiles()
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
    private fun validateUploadedFiles() {
        val request = assembleRequest(
            props.cliContext,
            props.uploadedFiles
                .filterNot(ValidationOutcome::isValidated)
                .map(ValidationOutcome::getFileInfo)
                .onEach {
                    props.toggleValidationInProgress(true, it)
                }
        )
        if (props.sessionId.isNotBlank()) { request.setSessionId(props.sessionId) }
        mainScope.launch {
            val validationResponse = sendValidationRequest(request)
            if (validationResponse.getSessionId().isNotEmpty()) props.setSessionId(validationResponse.getSessionId())
            validationResponse.getOutcomes().forEach { outcome ->
                props.addValidationOutcome(outcome)
                props.toggleValidationInProgress(true, outcome.getFileInfo())
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