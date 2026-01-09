package context

import api.getValidationPresets
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import model.*
import react.*

external interface ValidationContextValue {
    // State
    var validationContext: ValidationContext
    var sessionId: String
    var currentManualEntryText: String
    var manualValidationOutcome: ValidationOutcome?
    var manualValidatingInProgress: Boolean
    var uploadedFiles: List<ValidationOutcome>
    var presets: List<Preset>
    var igPackageInfoSet: Set<PackageInfo>
    var extensionSet: Set<String>
    var profileSet: Set<String>
    var bundleValidationRuleSet: Set<BundleValidationRule>

    // Callbacks
    var updateValidationContext: (ValidationContext, Boolean) -> Unit
    var setSessionId: (String) -> Unit
    var updateManualEntryText: (String) -> Unit
    var setManualValidationOutcome: (ValidationOutcome) -> Unit
    var toggleManualValidationInProgress: (Boolean) -> Unit
    var uploadFile: (FileInfo) -> Unit
    var deleteFile: (FileInfo) -> Unit
    var addValidationOutcome: (ValidationOutcome) -> Unit
    var toggleFileValidationInProgress: (Boolean, FileInfo) -> Unit
    var updateIgPackageInfoSet: (Set<PackageInfo>, Boolean) -> Unit
    var updateExtensionSet: (Set<String>, Boolean) -> Unit
    var updateProfileSet: (Set<String>, Boolean) -> Unit
    var updateBundleValidationRuleSet: (Set<BundleValidationRule>, Boolean) -> Unit
}

val ValidationContext = createContext<ValidationContextValue>()

external interface ValidationProviderProps : PropsWithChildren

class ValidationProviderState : State {
    // Core validation config
    var validationContext: ValidationContext = model.ValidationContext().setBaseEngine("DEFAULT")
    var sessionId: String = ""
    var presets: List<Preset> = emptyList()
    var igPackageInfoSet: Set<PackageInfo> = emptySet()
    var extensionSet: Set<String> = emptySet()
    var profileSet: Set<String> = emptySet()
    var bundleValidationRuleSet: Set<BundleValidationRule> = emptySet()

    // Manual entry state
    var currentManualEntryText: String = ""
    var manualValidationOutcome: ValidationOutcome? = null
    var manualValidatingInProgress: Boolean = false

    // File upload state
    var uploadedFiles: List<ValidationOutcome> = emptyList()
}

class ValidationProvider : RComponent<ValidationProviderProps, ValidationProviderState>() {

    private val mainScope = MainScope()

    init {
        state = ValidationProviderState()
        fetchPresets()
    }

    private fun fetchPresets() {
        mainScope.launch {
            try {
                val fetchedPresets = getValidationPresets()
                setState {
                    presets = fetchedPresets
                }
            } catch (e: Exception) {
                console.error("Failed to fetch presets", e)
            }
        }
    }

    override fun RBuilder.render() {
        // Create callback functions
        val updateValidationContext: (model.ValidationContext, Boolean) -> Unit = { ctx, resetBaseEngine ->
            val finalCtx = if (resetBaseEngine) ctx.setBaseEngine(null) else ctx
            setState { validationContext = finalCtx }
        }

        val setSessionId: (String) -> Unit = { id ->
            setState { sessionId = id }
        }

        val updateManualEntryText: (String) -> Unit = { text ->
            setState { currentManualEntryText = text }
        }

        val setManualValidationOutcome: (ValidationOutcome) -> Unit = { outcome ->
            setState { manualValidationOutcome = outcome }
        }

        val toggleManualValidationInProgress: (Boolean) -> Unit = { inProgress ->
            setState { manualValidatingInProgress = inProgress }
        }

        val uploadFile: (FileInfo) -> Unit = { fileInfo ->
            setState {
                uploadedFiles = uploadedFiles + ValidationOutcome()
                    .setFileInfo(fileInfo)
                    .setValidated(false)
                    .setValidating(false)
            }
        }

        val deleteFile: (FileInfo) -> Unit = { fileInfo ->
            setState {
                uploadedFiles = uploadedFiles.filterNot {
                    it.getFileInfo().fileName == fileInfo.fileName
                }
            }
        }

        val addValidationOutcome: (ValidationOutcome) -> Unit = { outcome ->
            setState {
                uploadedFiles = uploadedFiles.map { existing ->
                    if (existing.getFileInfo().fileName == outcome.getFileInfo().fileName) {
                        outcome.setValidated(true)
                    } else {
                        existing
                    }
                }
            }
        }

        val toggleFileValidationInProgress: (Boolean, FileInfo) -> Unit = { inProgress, fileInfo ->
            setState {
                uploadedFiles = uploadedFiles.map { outcome ->
                    if (outcome.getFileInfo().fileName == fileInfo.fileName) {
                        outcome.setValidating(inProgress)
                    } else {
                        outcome
                    }
                }
            }
        }

        val updateIgPackageInfoSet: (Set<PackageInfo>, Boolean) -> Unit = { set, resetBaseEngine ->
            setState {
                igPackageInfoSet = set
                if (resetBaseEngine) {
                    validationContext = validationContext.setBaseEngine(null)
                }
            }
        }

        val updateExtensionSet: (Set<String>, Boolean) -> Unit = { set, resetBaseEngine ->
            setState {
                extensionSet = set
                if (resetBaseEngine) {
                    validationContext = validationContext.setBaseEngine(null)
                }
            }
        }

        val updateProfileSet: (Set<String>, Boolean) -> Unit = { set, resetBaseEngine ->
            setState {
                profileSet = set
                if (resetBaseEngine) {
                    validationContext = validationContext.setBaseEngine(null)
                }
            }
        }

        val updateBundleValidationRuleSet: (Set<BundleValidationRule>, Boolean) -> Unit = { set, resetBaseEngine ->
            setState {
                bundleValidationRuleSet = set
                if (resetBaseEngine) {
                    validationContext = validationContext.setBaseEngine(null)
                }
            }
        }

        // Create context value
        val contextValue = js("{}")
            .unsafeCast<ValidationContextValue>()
            .apply {
                this.validationContext = state.validationContext
                this.sessionId = state.sessionId
                this.currentManualEntryText = state.currentManualEntryText
                this.manualValidationOutcome = state.manualValidationOutcome
                this.manualValidatingInProgress = state.manualValidatingInProgress
                this.uploadedFiles = state.uploadedFiles
                this.presets = state.presets
                this.igPackageInfoSet = state.igPackageInfoSet
                this.extensionSet = state.extensionSet
                this.profileSet = state.profileSet
                this.bundleValidationRuleSet = state.bundleValidationRuleSet

                this.updateValidationContext = updateValidationContext
                this.setSessionId = setSessionId
                this.updateManualEntryText = updateManualEntryText
                this.setManualValidationOutcome = setManualValidationOutcome
                this.toggleManualValidationInProgress = toggleManualValidationInProgress
                this.uploadFile = uploadFile
                this.deleteFile = deleteFile
                this.addValidationOutcome = addValidationOutcome
                this.toggleFileValidationInProgress = toggleFileValidationInProgress
                this.updateIgPackageInfoSet = updateIgPackageInfoSet
                this.updateExtensionSet = updateExtensionSet
                this.updateProfileSet = updateProfileSet
                this.updateBundleValidationRuleSet = updateBundleValidationRuleSet
            }

        context.ValidationContext.Provider {
            attrs.value = contextValue
            props.children?.let { +it }
        }
    }
}

fun RBuilder.validationProvider(handler: ValidationProviderProps.() -> Unit) {
    return child(ValidationProvider::class) {
        this.attrs(handler)
    }
}
