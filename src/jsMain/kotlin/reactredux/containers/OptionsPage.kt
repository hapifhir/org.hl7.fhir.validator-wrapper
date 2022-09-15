package reactredux.containers

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
}

private interface OptionsPageDispatchProps : Props {
    var updateCliContext: (CliContext) -> Unit
    var updateSelectedIgPackageInfo: (Set<PackageInfo>) -> Unit
}

val optionsPage: ComponentClass<Props> =
    rConnect<AppState, RAction, WrapperAction, Props, OptionsPageStateProps, OptionsPageDispatchProps, OptionsPageProps>(
        { state, _ ->
            cliContext = state.validationContextSlice.cliContext
            selectedIgPackageInfo = state.validationContextSlice.selectedIgPackageInfo
        },
        { dispatch, _ ->
            updateCliContext = {
                dispatch(ValidationContextSlice.UpdateCliContext(it))
            }
            updateSelectedIgPackageInfo = {
                dispatch(ValidationContextSlice.UpdateSelectedIgPackageInfo(it))
            }
        }
    )(OptionsPage::class.js.unsafeCast<ComponentClass<OptionsPageProps>>())