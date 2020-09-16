package reactredux.containers

import model.CliContext
import model.FileInfo
import model.ValidationOutcome
import react.RClass
import react.RProps
import react.invoke
import react.redux.rConnect
import reactredux.actions.RemoveFile
import reactredux.actions.UpdateContext
import reactredux.state.AppState
import redux.RAction
import redux.WrapperAction
import ui.components.ContextSettingsComponent
import ui.components.ContextSettingsProps
import ui.components.FileListComponent
import ui.components.FileListProps

private interface ContextSettingsStateProps : RProps {
    var cliContext: CliContext
}

private interface ContextSettingsDispatchProps : RProps {
    var update: (CliContext) -> Unit
}

val contextSettings: RClass<RProps> =
    rConnect<AppState, RAction, WrapperAction, RProps, ContextSettingsStateProps, ContextSettingsDispatchProps, ContextSettingsProps>(
        { state, _ ->
            cliContext = state.cliContext
        },
        { dispatch, _ ->
            update = { dispatch(UpdateContext(it)) }
        }
    )(ContextSettingsComponent::class.js.unsafeCast<RClass<ContextSettingsProps>>())