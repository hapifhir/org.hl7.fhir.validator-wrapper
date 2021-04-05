package reactredux.containers

import model.CliContext
import react.RClass
import react.RProps
import react.invoke
import react.redux.rConnect
import reactredux.slices.ValidationContextSlice
import reactredux.store.AppState
import redux.RAction
import redux.WrapperAction
import ui.components.options.OptionsPage
import ui.components.options.OptionsPageProps

private interface OptionsPageStateProps : RProps {
    var cliContext: CliContext
}

private interface OptionsPageDispatchProps : RProps {
    var update: (CliContext) -> Unit
}

val contextSettings: RClass<RProps> =
    rConnect<AppState, RAction, WrapperAction, RProps, OptionsPageStateProps, OptionsPageDispatchProps, OptionsPageProps>(
        { state, _ ->
            cliContext = state.validationContextSlice.cliContext
        },
        { dispatch, _ ->
            update = {
                dispatch(ValidationContextSlice.UpdateContext(it))
            }
        }
    )(OptionsPage::class.js.unsafeCast<RClass<OptionsPageProps>>())