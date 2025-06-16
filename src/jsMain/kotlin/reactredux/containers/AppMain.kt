package reactredux.containers

import App
import AppProps
import Polyglot
import model.AppScreen
import model.ValidationContext
import model.Preset
import react.*
import react.redux.rConnect
import reactredux.slices.LocalizationSlice
import reactredux.slices.PresetsSlice
import reactredux.slices.ValidationContextSlice
import reactredux.store.AppState
import redux.RAction
import redux.WrapperAction
import utils.Language

private interface AppStateProps : Props {
    var appScreen: AppScreen
    var polyglot: Polyglot
    var presets: List<Preset>
    var selectedLanguage: Language
    var validationContext: ValidationContext
}

private interface AppDispatchProps : Props {
    var fetchPolyglot: (String) -> Unit
    var fetchPresets: () -> Unit
    var setLanguage: (Language) -> Unit
    var updateValidationContext: (ValidationContext, Boolean) -> Unit
}

val app: ComponentClass<Props> =
    rConnect<AppState, RAction, WrapperAction, Props, AppStateProps, AppDispatchProps, AppProps>(
        mapStateToProps = { state, _ ->
            appScreen = state.appScreenSlice.appScreen
            polyglot = state.localizationSlice.polyglotInstance
            presets = state.presetsSlice.presets
            validationContext = state.validationContextSlice.validationContext
        },
        { dispatch, _ ->
            fetchPolyglot = { dispatch(LocalizationSlice.fetchPolyglot(it)) }
            fetchPresets = { dispatch(PresetsSlice.fetchPresets()) }
            setLanguage = { dispatch(LocalizationSlice.SetLanguage(it)) }
            updateValidationContext = { validationContext: ValidationContext, resetBaseEngine: Boolean -> dispatch(ValidationContextSlice.UpdateValidationContext(validationContext, resetBaseEngine)) }
        }
    )(App::class.js.unsafeCast<ComponentClass<AppProps>>())