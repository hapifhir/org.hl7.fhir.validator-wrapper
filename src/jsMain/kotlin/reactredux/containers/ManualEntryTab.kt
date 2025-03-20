package reactredux.containers

import Polyglot
import model.*
import react.ComponentClass
import react.Props
import react.invoke
import react.redux.rConnect
import reactredux.slices.ManualEntrySlice
import reactredux.slices.ValidationContextSlice
import reactredux.slices.ValidationSessionSlice
import reactredux.store.AppState
import redux.RAction
import redux.WrapperAction
import ui.components.tabs.entrytab.ManualEntryTab
import ui.components.tabs.entrytab.ManualEntryTabProps
import utils.Language

private interface ManualEntryTabStateProps : Props {
    var cliContext: CliContext
    var validationOutcome: ValidationOutcome?
    var currentManuallyEnteredText: String
    var validatingManualEntryInProgress: Boolean
    var language: Language
    var polyglot: Polyglot
    var sessionId: String
    var presets: List<Preset>
}

private interface ManualEntryTabDispatchProps : Props {
    var setValidationOutcome: (ValidationOutcome) -> Unit
    var toggleValidationInProgress: (Boolean) -> Unit
    var updateCurrentlyEnteredText: (String) -> Unit
    var updateCliContext: (CliContext) -> Unit
    var updateIgPackageInfoSet: (Set<PackageInfo>) -> Unit
    var updateExtensionSet: (Set<String>) -> Unit
    var updateProfileSet: (Set<String>)-> Unit
    var updateBundleValidationRuleSet: (Set<BundleValidationRule>) -> Unit
    var setSessionId: (String) -> Unit
}

val manualEntryTab: ComponentClass<Props> =
    rConnect<AppState, RAction, WrapperAction, Props, ManualEntryTabStateProps, ManualEntryTabDispatchProps, ManualEntryTabProps>(
        { state, _ ->
            cliContext = state.validationContextSlice.cliContext
            validationOutcome = state.manualEntrySlice.validationOutcome
            currentManuallyEnteredText = state.manualEntrySlice.currentManuallyEnteredText
            validatingManualEntryInProgress = state.manualEntrySlice.validatingManualEntryInProgress
            language = state.localizationSlice.selectedLanguage
            polyglot = state.localizationSlice.polyglotInstance
            sessionId = state.validationSessionSlice.sessionId
            presets = state.presetsSlice.presets
        },
        { dispatch, _ ->
            setValidationOutcome = { dispatch(ManualEntrySlice.AddManualEntryOutcome(it)) }
            toggleValidationInProgress = { dispatch(ManualEntrySlice.ToggleValidationInProgress(it)) }
            updateCurrentlyEnteredText = { dispatch(ManualEntrySlice.UpdateCurrentlyEnteredText(it)) }
            updateCliContext = {  dispatch(ValidationContextSlice.UpdateCliContext(it, false)) }
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
            setSessionId = { id: String -> dispatch(ValidationSessionSlice.SetSessionId(id)) }
        }
    )(ManualEntryTab::class.js.unsafeCast<ComponentClass<ManualEntryTabProps>>())