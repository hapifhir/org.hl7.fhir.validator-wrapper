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
import reactredux.slices.ValidationSessionSlice

private interface ResourceEntryFieldStateProps : RProps {
    var cliContext: CliContext
    var validationOutcome: ValidationOutcome
    var polyglot: Polyglot
    var sessionId: String
}

private interface ResourceEntryFieldDispatchProps : RProps {
    var addManuallyEnteredFileValidationOutcome: (ValidationOutcome) -> Unit
    var toggleValidationInProgress: (Boolean) -> Unit
    var setSessionId: (String) -> Unit
}

val resourceEntryField: RClass<RProps> =
    rConnect<AppState, RAction, WrapperAction, RProps, ResourceEntryFieldStateProps, ResourceEntryFieldDispatchProps, ResourceEntryFieldProps>(
        { state, _ ->
            cliContext = state.validationContextSlice.cliContext
            validationOutcome = state.manuallyEnteredResourceState.manuallyEnteredFileData
            polyglot = state.localizationState.polyglotInstance
            sessionId = state.validationSessionState.sessionId
        },
        { dispatch, _ ->
            addManuallyEnteredFileValidationOutcome = { dispatch(ManuallyEnteredResourceSlice.AddManuallyEnteredFileValidationOutcome(it)) }
            toggleValidationInProgress = { dispatch(ManuallyEnteredResourceSlice.ToggleValidationInProgress(it)) }
            setSessionId = { id: String -> dispatch(ValidationSessionSlice.SetSessionId(id)) }
        }
    )(ResourceEntryFieldComponent::class.js.unsafeCast<RClass<ResourceEntryFieldProps>>())