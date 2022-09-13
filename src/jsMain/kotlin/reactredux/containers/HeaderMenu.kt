package reactredux.containers

import Polyglot
import model.AppScreen
import react.ComponentClass
import react.RProps
import react.invoke
import react.redux.rConnect
import reactredux.slices.AppScreenSlice
import reactredux.slices.LocalizationSlice
import reactredux.store.AppState
import redux.RAction
import redux.WrapperAction
import ui.components.header.Header
import ui.components.header.HeaderProps
import utils.Language

private interface HeaderStateProps : RProps {
    var appScreen: AppScreen
    var language: Language
    var polyglot: Polyglot
}

private interface HeaderDispatchProps : RProps {
    var setScreen: (AppScreen) -> Unit
    var setPolyglot: (Polyglot) -> Unit
    var setLanguage: (Language) -> Unit
}

val header: ComponentClass<RProps> =
    rConnect<AppState, RAction, WrapperAction, RProps, HeaderStateProps, HeaderDispatchProps, HeaderProps>(
        { state, _ ->
            appScreen = state.appScreenSlice.appScreen
            language = state.localizationSlice.selectedLangauge
            polyglot = state.localizationSlice.polyglotInstance
        },
        { dispatch, _ ->
            setScreen = { dispatch(AppScreenSlice.SetScreen(it)) }
            setPolyglot = { dispatch(LocalizationSlice.SetPolyglot(it)) }
            setLanguage = { dispatch(LocalizationSlice.SetLanguage(it)) }
        }
    )(Header::class.js.unsafeCast<ComponentClass<HeaderProps>>())