package ui.components.tabs.uploadtab

import Polyglot
import api.getValidationPresets
import api.sendValidationRequest
import css.animation.FadeIn.fadeIn
import css.const.WHITE
import kotlinx.browser.document
import kotlinx.coroutines.launch
import kotlinx.css.*
import mainScope
import model.*
import org.w3c.dom.HTMLInputElement
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import ui.components.options.presetSelect
import ui.components.tabs.heading
import ui.components.tabs.uploadtab.filelist.fileEntryList
import ui.components.validation.validationOutcomePopup
import utils.Language
import utils.assembleRequest

external interface FileUploadTabProps : Props {
    var uploadedFiles: List<ValidationOutcome>
    var cliContext: CliContext
    var sessionId: String
    var language: Language
    var polyglot: Polyglot

    var deleteFile: (FileInfo) -> Unit
    var uploadFile: (FileInfo) -> Unit
    var toggleValidationInProgress: (Boolean, FileInfo) -> Unit
    var addValidationOutcome: (ValidationOutcome) -> Unit

    var updateCliContext: (CliContext) -> Unit
    var updateIgPackageInfoSet: (Set<PackageInfo>) -> Unit
    var updateExtensionSet: (Set<String>) -> Unit
    var updateProfileSet: (Set<String>) -> Unit
    var updateBundleValidationRuleSet: (Set<BundleValidationRule>) -> Unit
    var setSessionId: (String) -> Unit
    var presets: List<Preset>
}

class FileUploadTabState : State {
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
                +FileUploadTabStyle.tabContent
            }
            heading {
                text =  props.polyglot.t("upload_files_title") +  " (${props.uploadedFiles.size})"
            }
            fileEntryList {
                polyglot = props.polyglot
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
            styledDiv {
                css {
                    +FileUploadTabStyle.buttonBarContainer
                }
                fileUploadButton {
                    polyglot = props.polyglot
                    onUploadRequested = {
                        (document.getElementById(FILE_UPLOAD_ELEMENT_ID) as HTMLInputElement).click()
                    }
                }

                styledDiv {
                    css {
                        +FileUploadTabStyle.buttonBarDivider
                    }
                }

                fileValidateButton {
                    polyglot = props.polyglot
                    onValidateRequested = {
                        validateUploadedFiles()
                    }
                }

                styledDiv{
                    css {
                        +FileUploadTabStyle.buttonBarDivider
                    }
                }

                presetSelect{
                    cliContext = props.cliContext
                    updateCliContext = props.updateCliContext
                    updateIgPackageInfoSet = props.updateIgPackageInfoSet
                    updateExtensionSet = props.updateExtensionSet
                    updateProfileSet = props.updateProfileSet
                    updateBundleValidationRuleSet = props.updateBundleValidationRuleSet
                    setSessionId = props.setSessionId
                    language = props.language
                    polyglot = props.polyglot
                    presets = props.presets
                }
            }

            uploadFilesComponent {
                onFileUpload = {
                    props.uploadFile(it)
                }
            }
            state.currentlyDisplayedValidationOutcome?.let {
                validationOutcomePopup {
                    polyglot = props.polyglot
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
        val cliContext: CliContext = Preset.getLocalizedCliContextFromPresets(props.cliContext, props.presets) ?: return
        val request = assembleRequest(
            cliContext = cliContext,
            files = props.uploadedFiles
                .filterNot(ValidationOutcome::isValidated)
                .map(ValidationOutcome::getFileInfo)
                .onEach {
                    props.toggleValidationInProgress(true, it)
                }
        )
        if (props.sessionId.isNotBlank()) {
            request.setSessionId(props.sessionId)
        }
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
fun RBuilder.fileUploadTab(handler: FileUploadTabProps.() -> Unit) {
    return child(FileUploadTab::class) {
        this.attrs(handler)
    }
}

/**
 * CSS
 */
object FileUploadTabStyle : StyleSheet("FileUploadTabStyle", isStatic = true) {
    val tabContent by css {
        backgroundColor = WHITE
        flexDirection = FlexDirection.column
        justifyContent = JustifyContent.flexStart
        display = Display.flex
        padding(horizontal = 32.px, vertical = 16.px)
        fadeIn()
    }
    val buttonBarContainer by css {
        display = Display.inlineFlex
        flexDirection = FlexDirection.row
        alignItems = Align.center
        padding(vertical = 16.px)
    }
    val buttonBarDivider by css {
        width = 16.px
    }
}