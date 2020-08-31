package reactredux.containers

import model.CliContext
import model.ValidationOutcome
import react.RClass
import react.RProps
import react.invoke
import react.redux.rConnect
import reactredux.actions.AddValidationOutcome
import reactredux.state.AppState
import redux.WrapperAction
import ui.components.ValidateFilesButton
import ui.components.ValidateFilesButtonProps

private interface ValidateFilesButtonStateProps : RProps {
    var cliContext: CliContext
    var uploadedFiles: List<ValidationOutcome>
}

private interface ValidateFilesButtonDispatchProps : RProps {
    var addValidationOutcome: (ValidationOutcome) -> Unit
}

val validateFilesButton: RClass<RProps> =
    rConnect<AppState, AddValidationOutcome, WrapperAction, RProps, ValidateFilesButtonStateProps, ValidateFilesButtonDispatchProps, ValidateFilesButtonProps>(
        { state, _ ->
            cliContext = state.cliContext
            uploadedFiles = state.uploadedFiles
        },
        { dispatch, _ ->
            addValidationOutcome = { dispatch(AddValidationOutcome(it)) }
        }
    )(ValidateFilesButton::class.js.unsafeCast<RClass<ValidateFilesButtonProps>>())