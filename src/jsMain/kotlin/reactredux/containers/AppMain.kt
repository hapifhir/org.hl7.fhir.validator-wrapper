package reactredux.containers

import App
import AppProps
import Polyglot
import model.AppScreen
import model.CliContext
import react.*
import react.redux.rConnect
import reactredux.slices.LocalizationSlice
import reactredux.slices.ValidationContextSlice
import reactredux.store.AppState
import redux.RAction
import redux.WrapperAction
import utils.Language

private interface AppStateProps : Props {
    var appScreen: AppScreen
    var polyglot: Polyglot
    var selectedLanguage: Language
    var cliContext: CliContext
}

private interface AppDispatchProps : Props {
    var fetchPolyglot: (String) -> Unit
    var setLanguage: (Language) -> Unit
    var updateCliContext: (CliContext) -> Unit
}

val app: ComponentClass<Props> =
    rConnect<AppState, RAction, WrapperAction, Props, AppStateProps, AppDispatchProps, AppProps>(
        mapStateToProps = { state, _ ->
            appScreen = state.appScreenSlice.appScreen
            polyglot = state.localizationSlice.polyglotInstance
            cliContext = state.validationContextSlice.cliContext
        },
        { dispatch, _ ->
            fetchPolyglot = { dispatch(LocalizationSlice.fetchPolyglot(it)) }
            setLanguage = { dispatch(LocalizationSlice.SetLanguage(it)) }
            updateCliContext = { dispatch(ValidationContextSlice.UpdateCliContext(it)) }
        }
    )(App::class.js.unsafeCast<ComponentClass<AppProps>>())