package reactredux.containers

import Polyglot
import model.CliContext
import model.ValidationOutcome
import react.ComponentClass
import react.Props
import react.invoke
import react.redux.rConnect
import reactredux.slices.ManualEntrySlice
import reactredux.slices.ValidationSessionSlice
import reactredux.store.AppState
import redux.RAction
import redux.WrapperAction
import ui.components.tabs.entrytab.ManualEntryTab
import ui.components.tabs.entrytab.ManualEntryTabProps

private interface ManualEntryTabStateProps : Props {
    var cliContext: CliContext
    var validationOutcome: ValidationOutcome?
    var currentManuallyEnteredText: String
    var validatingManualEntryInProgress: Boolean
    var polyglot: Polyglot
    var sessionId: String
}

private interface ManualEntryTabDispatchProps : Props {
    var setValidationOutcome: (ValidationOutcome) -> Unit
    var toggleValidationInProgress: (Boolean) -> Unit
    var updateCurrentlyEnteredText: (String) -> Unit
    var setSessionId: (String) -> Unit
}

val manualEntryTab: ComponentClass<Props> =
    rConnect<AppState, RAction, WrapperAction, Props, ManualEntryTabStateProps, ManualEntryTabDispatchProps, ManualEntryTabProps>(
        { state, _ ->
            cliContext = state.validationContextSlice.cliContext
            validationOutcome = state.manualEntrySlice.validationOutcome
            currentManuallyEnteredText = state.manualEntrySlice.currentManuallyEnteredText
            validatingManualEntryInProgress = state.manualEntrySlice.validatingManualEntryInProgress
            polyglot = state.localizationSlice.polyglotInstance
            sessionId = state.validationSessionSlice.sessionId
        },
        { dispatch, _ ->
            setValidationOutcome = { dispatch(ManualEntrySlice.AddManualEntryOutcome(it)) }
            toggleValidationInProgress = { dispatch(ManualEntrySlice.ToggleValidationInProgress(it)) }
            updateCurrentlyEnteredText = { dispatch(ManualEntrySlice.UpdateCurrentlyEnteredText(it)) }
            setSessionId = { id: String -> dispatch(ValidationSessionSlice.SetSessionId(id)) }
        }
    )(ManualEntryTab::class.js.unsafeCast<ComponentClass<ManualEntryTabProps>>())