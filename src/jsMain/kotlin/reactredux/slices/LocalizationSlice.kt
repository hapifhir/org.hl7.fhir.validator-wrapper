package reactredux.slices

import Polyglot
import reactredux.store.RThunk
import reactredux.thunk.FetchPolyglotThunk
import redux.RAction
import utils.Language

object LocalizationSlice {

    data class State(
        val polyglotInstance: Polyglot = Polyglot(),
        val selectedLangauge: Language = Language.US_ENGLISH,
    )

    fun fetchPolyglot( localeString : String): RThunk {
        val fetchPolyglotThunk = FetchPolyglotThunk(localeString)
        return fetchPolyglotThunk
    }

    data class SetPolyglot(val polyglotInstance: Polyglot) : RAction
    data class SetLanguage(val selectedLangauge: Language) : RAction

    fun reducer(state: State = State(), action: RAction): State {
        return when (action) {
            is SetPolyglot -> {
                println("setting new polyglot instance\nexisting -> ${state.polyglotInstance.t("heading_validate")}")
                println("setting new polyglot instance\nnew -> ${action.polyglotInstance.t("heading_validate")}")
                state.copy(polyglotInstance = action.polyglotInstance)
            }
            is SetLanguage -> {
                println("setting new lang instance\nexisting -> ${state.selectedLangauge}")
                println("setting new lang instance\nnew -> ${action.selectedLangauge}")
                state.copy(selectedLangauge = action.selectedLangauge)
            }
            else -> state
        }
    }
}