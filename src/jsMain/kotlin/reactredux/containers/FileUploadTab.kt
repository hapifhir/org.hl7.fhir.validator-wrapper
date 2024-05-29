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
    var cliContext: CliContext
    var sessionId: String
    var language: Language
    var polyglot: Polyglot
}

private interface FileUploadTabDispatchProps : Props {
    var deleteFile: (FileInfo) -> Unit
    var uploadFile: (FileInfo) -> Unit
    var setSessionId: (String) -> Unit
    var toggleValidationInProgress: (Boolean, FileInfo) -> Unit
    var addValidationOutcome: (ValidationOutcome) -> Unit

    var updateCliContext: (CliContext) -> Unit
    var updateIgPackageInfoSet: (Set<PackageInfo>) -> Unit
    var updateExtensionSet: (Set<String>) -> Unit
    var updateProfileSet: (Set<String>)-> Unit
    var updateBundleValidationRuleSet: (Set<BundleValidationRule>) -> Unit
}

val fileUploadTab: ComponentClass<Props> =
    rConnect<AppState, RAction, WrapperAction, Props, FileUploadTabProps, FileUploadTabDispatchProps, FileUploadTabProps>(
        { state, _ ->
            uploadedFiles = state.uploadedResourceSlice.uploadedFiles
            cliContext = state.validationContextSlice.cliContext
            sessionId = state.validationSessionSlice.sessionId
            language = state.localizationSlice.selectedLanguage
            polyglot = state.localizationSlice.polyglotInstance
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
            updateCliContext = {
                dispatch(ValidationContextSlice.UpdateCliContext(it))
            }
            updateIgPackageInfoSet = {
                dispatch(ValidationContextSlice.UpdateIgPackageInfoSet(it))
            }
            updateExtensionSet = {
                dispatch(ValidationContextSlice.UpdateExtensionSet(it))
            }
            updateProfileSet = {
                dispatch(ValidationContextSlice.UpdateProfileSet(it))
            }
            updateBundleValidationRuleSet = {
                dispatch(ValidationContextSlice.UpdateBundleValidationRuleSet(it))
            }
        }
    )(FileUploadTab::class.js.unsafeCast<ComponentClass<FileUploadTabProps>>())