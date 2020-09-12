package reactredux.containers

import App
import AppProps
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

private interface AppStateProps : RProps {
    var appScreen: AppScreen
}

private interface AppDispatchProps : RProps {

}

val app: RClass<RProps> =
    rConnect<AppState, RAction, WrapperAction, RProps, AppStateProps, AppDispatchProps, AppProps>(
        { state, _ ->
            appScreen = state.appScreen
        },
        { dispatch, _ ->

        }
    )(App::class.js.unsafeCast<RClass<AppProps>>())