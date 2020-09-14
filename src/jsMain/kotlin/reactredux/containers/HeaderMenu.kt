package reactredux.containers

import model.AppScreen
import model.FileInfo
import model.ValidationOutcome
import react.RClass
import react.RProps
import react.invoke
import react.redux.rConnect
import reactredux.actions.RemoveFile
import reactredux.actions.SetScreen
import reactredux.state.AppState
import redux.RAction
import redux.WrapperAction
import ui.components.FileListComponent
import ui.components.FileListProps
import ui.components.Header
import ui.components.HeaderProps

private interface HeaderStateProps : RProps {
    var appScreen: AppScreen
}

private interface HeaderDispatchProps : RProps {
    var setScreen: (AppScreen) -> Unit
}

val header: RClass<RProps> =
    rConnect<AppState, RAction, WrapperAction, RProps, HeaderStateProps, HeaderDispatchProps, HeaderProps>(
        { state, _ ->
            appScreen = state.appScreen
        },
        { dispatch, _ ->
            setScreen = { dispatch(SetScreen(it)) }
        }
    )(Header::class.js.unsafeCast<RClass<HeaderProps>>())