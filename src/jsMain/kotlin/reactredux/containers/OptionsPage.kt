package reactredux.containers

import Polyglot
import model.BundleValidationRule
import model.ValidationContext
import model.PackageInfo
import model.ValidationEngineSettings
import react.ComponentClass
import react.Props
import react.invoke
import react.redux.rConnect
import reactredux.slices.ValidationContextSlice
import reactredux.slices.ValidationSessionSlice
import reactredux.store.AppState
import redux.RAction
import redux.WrapperAction
import ui.components.options.OptionsPage
import ui.components.options.OptionsPageProps

private interface OptionsPageStateProps : Props {
    var validationEngineSettings: ValidationEngineSettings
    var validationContext: ValidationContext
    var igPackageInfoSet: Set<PackageInfo>
    var extensionSet: Set<String>
    var profileSet: Set<String>
    var bundleValidationRuleSet: Set<BundleValidationRule>
    var polyglot: Polyglot
}

private interface OptionsPageDispatchProps : Props {
    var updateValidationEngineSettings: (ValidationEngineSettings) -> Unit
    var updateValidationContext: (ValidationContext) -> Unit
    var updateIgPackageInfoSet: (Set<PackageInfo>) -> Unit
    var updateExtensionSet: (Set<String>) -> Unit
    var setSessionId: (String) -> Unit
    var updateProfileSet: (Set<String>) -> Unit
    var updateBundleValidationRuleSet: (Set<BundleValidationRule>) -> Unit

}

val optionsPage: ComponentClass<Props> =
    rConnect<AppState, RAction, WrapperAction, Props, OptionsPageStateProps, OptionsPageDispatchProps, OptionsPageProps>(
        { state, _ ->
            validationEngineSettings = state.validationContextSlice.validationEngineSettings
            validationContext = state.validationContextSlice.validationContext
            igPackageInfoSet = state.validationContextSlice.igPackageInfoSet
            extensionSet = state.validationContextSlice.extensionSet
            profileSet = state.validationContextSlice.profileSet
            bundleValidationRuleSet = state.validationContextSlice.bundleValidationRuleSet
            polyglot = state.localizationSlice.polyglotInstance
        },
        { dispatch, _ ->
            updateValidationEngineSettings = { dispatch(ValidationContextSlice.UpdateValidationEngineSettings(it, true)) }
            updateValidationContext = { dispatch(ValidationContextSlice.UpdateValidationContext(it, true)) }
            updateIgPackageInfoSet = {
                dispatch(ValidationContextSlice.UpdateIgPackageInfoSet(it))
            }
            updateExtensionSet = {
                dispatch(ValidationContextSlice.UpdateExtensionSet(it))
            }
            setSessionId = { id: String -> dispatch(ValidationSessionSlice.SetSessionId(id)) }
            updateProfileSet = {
                dispatch(ValidationContextSlice.UpdateProfileSet(it))
            }
            updateBundleValidationRuleSet = {
                dispatch(ValidationContextSlice.UpdateBundleValidationRuleSet(it))
            }
        }
    )(OptionsPage::class.js.unsafeCast<ComponentClass<OptionsPageProps>>())