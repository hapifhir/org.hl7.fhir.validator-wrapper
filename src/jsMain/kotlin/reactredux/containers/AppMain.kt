package reactredux.containers

import App
import AppProps
import Polyglot
import model.AppScreen
import react.*
import react.redux.rConnect
import reactredux.store.AppState
import redux.RAction
import redux.WrapperAction

private interface AppStateProps : Props {
    var appScreen: AppScreen
    var polyglot: Polyglot
}

private interface AppDispatchProps : Props

val app: ComponentClass<Props> =
    rConnect<AppState, RAction, WrapperAction, Props, AppStateProps, AppDispatchProps, AppProps>(
        mapStateToProps = { state, _ ->
            appScreen = state.appScreenSlice.appScreen
            polyglot = state.localizationSlice.polyglotInstance
        },
        { _, _ ->
        }
    )(App::class.js.unsafeCast<ComponentClass<AppProps>>())