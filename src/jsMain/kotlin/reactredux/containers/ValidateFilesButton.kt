package reactredux.containers

import model.CliContext
import model.FileInfo
import model.ValidationOutcome
import react.RClass
import react.RProps
import react.invoke
import react.redux.rConnect
import reactredux.slices.UploadedResourceSlice
import reactredux.slices.ValidationSessionSlice
import reactredux.store.AppState
import redux.RAction
import redux.WrapperAction
import ui.components.ValidateFilesButton
import ui.components.ValidateFilesButtonProps

private interface ValidateFilesButtonStateProps : RProps {
    var cliContext: CliContext
    var uploadedFiles: List<ValidationOutcome>
    var sessionId: String
}

private interface ValidateFilesButtonDispatchProps : RProps {
    var addValidationOutcome: (ValidationOutcome) -> Unit
    var toggleValidationInProgress: (Boolean, FileInfo) -> Unit
    var setSessionId: (String) -> Unit
}

val validateFilesButton: RClass<RProps> =
    rConnect<AppState, RAction, WrapperAction, RProps, ValidateFilesButtonStateProps, ValidateFilesButtonDispatchProps, ValidateFilesButtonProps>(
        { state, _ ->
            cliContext = state.validationContextSlice.cliContext
            uploadedFiles = state.uploadedResourceSlice.uploadedFiles
            sessionId = state.validationSessionSlice.sessionId
        },
        { dispatch, _ ->
            addValidationOutcome = { dispatch(UploadedResourceSlice.AddValidationOutcome(it)) }
            toggleValidationInProgress = { b: Boolean, fileInfo: FileInfo -> dispatch(UploadedResourceSlice.ToggleValidationInProgress(b, fileInfo)) }
            setSessionId = { id: String -> dispatch(ValidationSessionSlice.SetSessionId(id)) }
        }
    )(ValidateFilesButton::class.js.unsafeCast<RClass<ValidateFilesButtonProps>>())