package reactredux.containers

import model.CliContext
import model.ValidationOutcome
import react.RClass
import react.RProps
import react.invoke
import react.redux.rConnect
import reactredux.actions.AddManuallyEnteredFileValidationOutcome
import reactredux.actions.ToggleManuallyEnteredValidationInProgress
import reactredux.state.AppState
import redux.RAction
import redux.WrapperAction
import ui.components.ResourceEntryFieldComponent
import ui.components.ResourceEntryFieldProps

private interface ResourceEntryFieldStateProps : RProps {
    var cliContext: CliContext
    var validationOutcome: ValidationOutcome
}

private interface ResourceEntryFieldDispatchProps : RProps {
    var addManuallyEnteredFileValidationOutcome: (ValidationOutcome) -> Unit
    var toggleValidationInProgress: (Boolean) -> Unit
}

val resourceEntryField: RClass<RProps> =
    rConnect<AppState, RAction, WrapperAction, RProps, ResourceEntryFieldStateProps, ResourceEntryFieldDispatchProps, ResourceEntryFieldProps>(
        { state, _ ->
            cliContext = state.cliContext
            validationOutcome = state.manuallyEnteredFile
        },
        { dispatch, _ ->
            addManuallyEnteredFileValidationOutcome = { dispatch(AddManuallyEnteredFileValidationOutcome(it)) }
            toggleValidationInProgress = { dispatch(ToggleManuallyEnteredValidationInProgress(it)) }
        }
    )(ResourceEntryFieldComponent::class.js.unsafeCast<RClass<ResourceEntryFieldProps>>())