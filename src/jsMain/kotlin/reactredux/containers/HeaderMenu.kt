package reactredux.containers

import Polyglot
import model.AppScreen
import model.CliContext
import react.ComponentClass
import react.Props
import react.invoke
import react.redux.rConnect
import reactredux.slices.AppScreenSlice
import reactredux.slices.LocalizationSlice
import reactredux.slices.ValidationContextSlice
import reactredux.store.AppState
import redux.RAction
import redux.WrapperAction
import ui.components.header.Header
import ui.components.header.HeaderProps
import utils.Language

private interface HeaderStateProps : Props {
    var appScreen: AppScreen
    var selectedLanguage: Language
    var polyglot: Polyglot
    var cliContext: CliContext
}

private interface HeaderDispatchProps : Props {
    var setScreen: (AppScreen) -> Unit
    var fetchPolyglot: (String) -> Unit
    var setPolyglot: (Polyglot) -> Unit
    var setLanguage: (Language) -> Unit
    var updateCliContext: (CliContext) -> Unit
}

val header: ComponentClass<Props> =
    rConnect<AppState, RAction, WrapperAction, Props, HeaderStateProps, HeaderDispatchProps, HeaderProps>(
        { state, _ ->
            appScreen = state.appScreenSlice.appScreen
            selectedLanguage = state.localizationSlice.selectedLanguage
            polyglot = state.localizationSlice.polyglotInstance
            cliContext = state.validationContextSlice.cliContext
        },
        { dispatch, _ ->
            setScreen = { dispatch(AppScreenSlice.SetScreen(it)) }
            fetchPolyglot = { dispatch(LocalizationSlice.fetchPolyglot(it)) }
            setPolyglot = { dispatch(LocalizationSlice.SetPolyglot(it)) }
            setLanguage = { dispatch(LocalizationSlice.SetLanguage(it)) }
            updateCliContext = { dispatch(ValidationContextSlice.UpdateCliContext(it)) }
        }
    )(Header::class.js.unsafeCast<ComponentClass<HeaderProps>>())