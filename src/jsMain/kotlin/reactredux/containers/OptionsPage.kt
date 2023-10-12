package reactredux.containers

import Polyglot
import model.CliContext
import model.PackageInfo
import react.ComponentClass
import react.Props
import react.invoke
import react.redux.rConnect
import reactredux.slices.ValidationContextSlice
import reactredux.store.AppState
import redux.RAction
import redux.WrapperAction
import ui.components.options.OptionsPage
import ui.components.options.OptionsPageProps

private interface OptionsPageStateProps : Props {
    var cliContext: CliContext
    var selectedIgPackageInfo: Set<PackageInfo>
    var addedExtensionInfo: Set<String>
    var addedProfiles: Set<String>
    var polyglot: Polyglot
}

private interface OptionsPageDispatchProps : Props {
    var updateCliContext: (CliContext) -> Unit
    var updateSelectedIgPackageInfo: (Set<PackageInfo>) -> Unit
    var updateAddedExtensionUrl: (Set<String>) -> Unit
    var updateAddedProfiles: (Set<String>) -> Unit
}

val optionsPage: ComponentClass<Props> =
    rConnect<AppState, RAction, WrapperAction, Props, OptionsPageStateProps, OptionsPageDispatchProps, OptionsPageProps>(
        { state, _ ->
            cliContext = state.validationContextSlice.cliContext
            selectedIgPackageInfo = state.validationContextSlice.selectedIgPackageInfo
            addedExtensionInfo = state.validationContextSlice.addedExtensionInfo
            addedProfiles = state.validationContextSlice.addedProfiles
            polyglot = state.localizationSlice.polyglotInstance
        },
        { dispatch, _ ->
            updateCliContext = {
                dispatch(ValidationContextSlice.UpdateCliContext(it))
            }
            updateSelectedIgPackageInfo = {
                dispatch(ValidationContextSlice.UpdateSelectedIgPackageInfo(it))
            }
            updateAddedExtensionUrl = {
                dispatch(ValidationContextSlice.UpdateAddedExtensionUrl(it))
            }
            updateAddedProfiles = {
                dispatch(ValidationContextSlice.UpdateAddedProfile(it))
            }
        }
    )(OptionsPage::class.js.unsafeCast<ComponentClass<OptionsPageProps>>())