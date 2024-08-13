package reactredux.containers

import Polyglot
import model.BundleValidationRule
import model.CliContext
import model.PackageInfo
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
    var cliContext: CliContext
    var igPackageInfoSet: Set<PackageInfo>
    var extensionSet: Set<String>
    var profileSet: Set<String>
    var bundleValidationRuleSet: Set<BundleValidationRule>
    var polyglot: Polyglot
}

private interface OptionsPageDispatchProps : Props {
    var updateCliContext: (CliContext) -> Unit
    var updateIgPackageInfoSet: (Set<PackageInfo>) -> Unit
    var updateExtensionSet: (Set<String>) -> Unit
    var setSessionId: (String) -> Unit
    var updateProfileSet: (Set<String>) -> Unit
    var updateBundleValidationRuleSet: (Set<BundleValidationRule>) -> Unit

}

val optionsPage: ComponentClass<Props> =
    rConnect<AppState, RAction, WrapperAction, Props, OptionsPageStateProps, OptionsPageDispatchProps, OptionsPageProps>(
        { state, _ ->
            cliContext = state.validationContextSlice.cliContext
            igPackageInfoSet = state.validationContextSlice.igPackageInfoSet
            extensionSet = state.validationContextSlice.extensionSet
            profileSet = state.validationContextSlice.profileSet
            bundleValidationRuleSet = state.validationContextSlice.bundleValidationRuleSet
            polyglot = state.localizationSlice.polyglotInstance
        },
        { dispatch, _ ->
            updateCliContext = { dispatch(ValidationContextSlice.UpdateCliContext(it, true)) }
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