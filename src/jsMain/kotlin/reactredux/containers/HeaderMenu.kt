package reactredux.containers

import model.AppScreen
import react.RClass
import react.RProps
import react.invoke
import react.redux.rConnect
import reactredux.store.AppState
import redux.RAction
import redux.WrapperAction
import ui.components.Header
import ui.components.HeaderProps
import utils.Language
import Polyglot
import reactredux.slices.AppScreenSlice
import reactredux.slices.LocalizationSlice

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

val header: RClass<RProps> =
    rConnect<AppState, RAction, WrapperAction, RProps, HeaderStateProps, HeaderDispatchProps, HeaderProps>(
        { state, _ ->
            appScreen = state.appScreenSlice.appScreen
            language = state.localizationState.selectedLangauge
            polyglot = state.localizationState.polyglotInstance
        },
        { dispatch, _ ->
            setScreen = { dispatch(AppScreenSlice.SetScreen(it)) }
            setPolyglot = { dispatch(LocalizationSlice.SetPolyglot(it)) }
            setLanguage = { dispatch(LocalizationSlice.SetLanguage(it)) }
        }
    )(Header::class.js.unsafeCast<RClass<HeaderProps>>())