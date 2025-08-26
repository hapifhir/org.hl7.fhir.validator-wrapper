package reactredux.containers

import Polyglot
import model.*
import react.ComponentClass
import react.Props
import react.invoke
import react.redux.rConnect
import reactredux.slices.UploadedResourceSlice
import reactredux.slices.ValidationContextSlice
import reactredux.slices.ValidationSessionSlice
import reactredux.store.AppState
import redux.RAction
import redux.WrapperAction
import ui.components.tabs.uploadtab.FileUploadTab
import utils.Language

private interface FileUploadTabProps : Props {
    var uploadedFiles: List<ValidationOutcome>
    var validationEngineSettings: ValidationEngineSettings
    var validationContext: ValidationContext
    var sessionId: String
    var language: Language
    var polyglot: Polyglot
    var presets: List<Preset>
}

private interface FileUploadTabDispatchProps : Props {
    var deleteFile: (FileInfo) -> Unit
    var uploadFile: (FileInfo) -> Unit
    var setSessionId: (String) -> Unit
    var toggleValidationInProgress: (Boolean, FileInfo) -> Unit
    var addValidationOutcome: (ValidationOutcome) -> Unit
    var updateValidationEngineSettings: (ValidationEngineSettings) -> Unit
    var updateValidationContext: (ValidationContext) -> Unit
    var updateIgPackageInfoSet: (Set<PackageInfo>) -> Unit
    var updateExtensionSet: (Set<String>) -> Unit
    var updateProfileSet: (Set<String>)-> Unit
    var updateBundleValidationRuleSet: (Set<BundleValidationRule>) -> Unit
}

val fileUploadTab: ComponentClass<Props> =
    rConnect<AppState, RAction, WrapperAction, Props, FileUploadTabProps, FileUploadTabDispatchProps, FileUploadTabProps>(
        { state, _ ->
            uploadedFiles = state.uploadedResourceSlice.uploadedFiles
            validationEngineSettings = state.validationContextSlice.validationEngineSettings
            validationContext = state.validationContextSlice.validationContext
            sessionId = state.validationSessionSlice.sessionId
            language = state.localizationSlice.selectedLanguage
            polyglot = state.localizationSlice.polyglotInstance
            presets = state.presetsSlice.presets
        },
        { dispatch, _ ->
            deleteFile = { dispatch(UploadedResourceSlice.RemoveFile(it)) }
            uploadFile = { dispatch(UploadedResourceSlice.UploadFile(it)) }
            setSessionId = { id: String -> dispatch(ValidationSessionSlice.SetSessionId(id)) }
            toggleValidationInProgress = { b: Boolean, fileInfo: FileInfo ->
                dispatch(UploadedResourceSlice.ToggleValidationInProgress(b,
                    fileInfo))
            }
            addValidationOutcome = { dispatch(UploadedResourceSlice.AddValidationOutcome(it)) }
            updateValidationEngineSettings = { dispatch(ValidationContextSlice.UpdateValidationEngineSettings(it, false)) }
            updateValidationContext = { dispatch(ValidationContextSlice.UpdateValidationContext(it, false)) }
            updateIgPackageInfoSet = {
                dispatch(ValidationContextSlice.UpdateIgPackageInfoSet(it, false))
            }
            updateExtensionSet = {
                dispatch(ValidationContextSlice.UpdateExtensionSet(it, false))
            }
            updateProfileSet = {
                dispatch(ValidationContextSlice.UpdateProfileSet(it, false))
            }
            updateBundleValidationRuleSet = {
                dispatch(ValidationContextSlice.UpdateBundleValidationRuleSet(it, false))
            }
        }
    )(FileUploadTab::class.js.unsafeCast<ComponentClass<FileUploadTabProps>>())