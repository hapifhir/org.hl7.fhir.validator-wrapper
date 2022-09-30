package reactredux.slices

import Polyglot
import redux.RAction
import utils.Language

object LocalizationSlice {


    fun getPolyglot ()  : Polyglot {
        console.log("getPolyglot")
        var polyglot = Polyglot(js("{locale: \"fr\"}"))
        polyglot.extend(phrases = js("{" +
                "'Options': 'Options en Francais'," +
                "'Validate': 'Validate'" +
                "}"))
        return polyglot
    }

    data class State(
        val polyglotInstance: Polyglot = getPolyglot(),
        val selectedLangauge: Language = Language.US_ENGLISH,
    )

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