package ui.components.tabs.uploadtab

import Polyglot
import api.sendValidationRequest
import context.LocalizationContext
import css.animation.FadeIn.fadeIn
import css.const.WHITE
import web.dom.document
import kotlinx.coroutines.launch
import kotlinx.css.*
import mainScope
import model.*
import web.html.HTMLInputElement
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
import utils.buildCompleteValidationContext

external interface FileUploadTabProps : Props {
    var polyglot: Polyglot
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
        context.ValidationContext.Consumer { validationContext ->
            LocalizationContext.Consumer { localizationContext ->
                val language = localizationContext?.selectedLanguage ?: Language.ENGLISH
                val uploadedFiles = validationContext?.uploadedFiles ?: emptyList()

                styledDiv {
                    css {
                        +FileUploadTabStyle.tabContent
                    }
                    heading {
                        text = props.polyglot.t("upload_files_title") + " (${uploadedFiles.size})"
                    }
                    fileEntryList {
                        polyglot = props.polyglot
                        validationOutcomes = uploadedFiles
                        viewFile = { outcomeToView ->
                            setState {
                                currentlyDisplayedValidationOutcome = outcomeToView
                            }
                        }
                        deleteFile = {
                            validationContext?.deleteFile?.invoke(it.getFileInfo())
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
                                validateUploadedFiles(
                                    validationContext?.validationContext
                                        ?: ValidationContext().setBaseEngine("DEFAULT"),
                                    validationContext?.sessionId ?: "",
                                    uploadedFiles,
                                    validationContext?.presets ?: emptyList(),
                                    validationContext?.igPackageInfoSet ?: emptySet(),
                                    validationContext?.profileSet ?: emptySet(),
                                    validationContext?.extensionSet ?: emptySet(),
                                    validationContext?.bundleValidationRuleSet ?: emptySet(),
                                    { id -> validationContext?.setSessionId?.invoke(id) },
                                    { outcome -> validationContext?.addValidationOutcome?.invoke(outcome) },
                                    { inProgress, fileInfo ->
                                        validationContext?.toggleFileValidationInProgress?.invoke(inProgress, fileInfo)
                                    }
                                )
                            }
                        }

                        styledDiv {
                            css {
                                +FileUploadTabStyle.buttonBarDivider
                            }
                        }

                        presetSelect {
                            this.validationContext = validationContext?.validationContext
                                ?: ValidationContext().setBaseEngine("DEFAULT")
                            updateValidationContext = { ctx, resetBaseEngine ->
                                validationContext?.updateValidationContext?.invoke(ctx, resetBaseEngine)
                            }
                            updateIgPackageInfoSet = { set, resetBaseEngine ->
                                validationContext?.updateIgPackageInfoSet?.invoke(set, resetBaseEngine)
                            }
                            updateExtensionSet = { set, resetBaseEngine ->
                                validationContext?.updateExtensionSet?.invoke(set, resetBaseEngine)
                            }
                            updateProfileSet = { set, resetBaseEngine ->
                                validationContext?.updateProfileSet?.invoke(set, resetBaseEngine)
                            }
                            updateBundleValidationRuleSet = { set, resetBaseEngine ->
                                validationContext?.updateBundleValidationRuleSet?.invoke(set, resetBaseEngine)
                            }
                            setSessionId = { id ->
                                validationContext?.setSessionId?.invoke(id)
                            }
                            this.language = language
                            polyglot = props.polyglot
                            presets = validationContext?.presets ?: emptyList()
                        }
                    }

                    uploadFilesComponent {
                        onFileUpload = {
                            validationContext?.uploadFile?.invoke(it)
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
        }
    }

    private fun validateUploadedFiles(
        validationContext: ValidationContext,
        sessionId: String,
        uploadedFiles: List<ValidationOutcome>,
        presets: List<Preset>,
        igPackageInfoSet: Set<PackageInfo>,
        profileSet: Set<String>,
        extensionSet: Set<String>,
        bundleValidationRuleSet: Set<BundleValidationRule>,
        setSessionId: (String) -> Unit,
        addValidationOutcome: (ValidationOutcome) -> Unit,
        toggleValidationInProgress: (Boolean, FileInfo) -> Unit
    ) {
        val completeValidationContext: ValidationContext = buildCompleteValidationContext(
            baseContext = validationContext,
            igPackageInfoSet = igPackageInfoSet,
            profileSet = profileSet,
            extensionSet = extensionSet,
            bundleValidationRuleSet = bundleValidationRuleSet,
            presets = presets
        )
        val request = assembleRequest(
            validationContext = completeValidationContext,
            files = uploadedFiles
                .filterNot(ValidationOutcome::isValidated)
                .map(ValidationOutcome::getFileInfo)
                .onEach {
                    toggleValidationInProgress(true, it)
                }
        )
        if (sessionId.isNotBlank()) {
            request.setSessionId(sessionId)
        }
        mainScope.launch {
            val validationResponse = sendValidationRequest(request)
            if (validationResponse.getSessionId().isNotEmpty()) setSessionId(validationResponse.getSessionId())
            validationResponse.getOutcomes().forEach { outcome ->
                addValidationOutcome(outcome)
                toggleValidationInProgress(true, outcome.getFileInfo())
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