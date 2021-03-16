package reactredux.containers

import App
import AppProps
import model.AppScreen
import react.RClass
import react.RProps
import react.invoke
import react.redux.rConnect
import reactredux.store.AppState
import redux.RAction
import redux.WrapperAction
import Polyglot

private interface AppStateProps : RProps {
    var appScreen: AppScreen
    var polyglot: Polyglot
}

private interface AppDispatchProps : RProps {}

val app: RClass<RProps> =
    rConnect<AppState, RAction, WrapperAction, RProps, AppStateProps, AppDispatchProps, AppProps>(
        { state, _ ->
            appScreen = state.appScreenSlice.appScreen
            polyglot = state.localizationSlice.polyglotInstance
        },
        { _, _ ->
        }
    )(App::class.js.unsafeCast<RClass<AppProps>>())