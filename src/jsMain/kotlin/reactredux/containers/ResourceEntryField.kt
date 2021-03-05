package reactredux.containers

import model.CliContext
import model.ValidationOutcome
import react.RClass
import react.RProps
import react.invoke
import react.redux.rConnect
import reactredux.store.AppState
import redux.RAction
import redux.WrapperAction
import ui.components.ResourceEntryFieldComponent
import ui.components.ResourceEntryFieldProps
import Polyglot
import reactredux.slices.ManuallyEnteredResourceSlice

private interface ResourceEntryFieldStateProps : RProps {
    var cliContext: CliContext
    var validationOutcome: ValidationOutcome
    var polyglot: Polyglot
}

private interface ResourceEntryFieldDispatchProps : RProps {
    var addManuallyEnteredFileValidationOutcome: (ValidationOutcome) -> Unit
    var toggleValidationInProgress: (Boolean) -> Unit
}

val resourceEntryField: RClass<RProps> =
    rConnect<AppState, RAction, WrapperAction, RProps, ResourceEntryFieldStateProps, ResourceEntryFieldDispatchProps, ResourceEntryFieldProps>(
        { state, _ ->
            cliContext = state.validationContextSlice.cliContext
            validationOutcome = state.manuallyEnteredResourceState.manuallyEnteredFileData
            polyglot = state.localizationState.polyglotInstance
        },
        { dispatch, _ ->
            addManuallyEnteredFileValidationOutcome = { dispatch(ManuallyEnteredResourceSlice.AddManuallyEnteredFileValidationOutcome(it)) }
            toggleValidationInProgress = { dispatch(ManuallyEnteredResourceSlice.ToggleValidationInProgress(it)) }
        }
    )(ResourceEntryFieldComponent::class.js.unsafeCast<RClass<ResourceEntryFieldProps>>())