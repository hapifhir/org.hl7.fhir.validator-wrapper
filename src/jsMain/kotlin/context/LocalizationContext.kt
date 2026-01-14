package context

import Polyglot
import api.getPolyglotPhrases
import kotlinx.browser.window
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonPrimitive
import model.ValidationContext
import react.*
import utils.Language
import utils.getJS

external interface LocalizationContextValue {
    var polyglot: Polyglot
    var selectedLanguage: Language
    var setLanguage: (Language) -> Unit
    var isLoading: Boolean
}

val LocalizationContext = createContext<LocalizationContextValue>()

external interface LocalizationProviderProps : PropsWithChildren

class LocalizationProviderState : State {
    var polyglot: Polyglot = Polyglot()
    var selectedLanguage: Language = Language.ENGLISH
    var isLoading: Boolean = false
}

class LocalizationProvider : RComponent<LocalizationProviderProps, LocalizationProviderState>() {

    private val mainScope = MainScope()

    init {
        state = LocalizationProviderState()
        // Auto-detect browser language on mount
        detectAndSetBrowserLanguage()
    }

    private fun detectAndSetBrowserLanguage() {
        var languageDetected = false
        for (item in window.navigator.languages) {
            val prefix = item.substring(0, 2)
            val detectedLanguage = Language.getSelectedLanguage(prefix)
            if (detectedLanguage != null) {
                fetchPolyglot(detectedLanguage)
                languageDetected = true
                break
            }
        }
        // If no supported language detected, default to English
        if (!languageDetected) {
            fetchPolyglot(Language.ENGLISH)
        }
    }

    private fun fetchPolyglot(language: Language) {
        setState {
            selectedLanguage = language
            isLoading = true
        }

        mainScope.launch {
            try {
                val phrases = getPolyglotPhrases(language.getLanguageCode())
                val phrasePairs: Array<Pair<String, Any?>> = phrases.entries.map {
                    Pair(it.key, (it.value as JsonPrimitive).content as Any?)
                }.toTypedArray()

                val newPolyglot = Polyglot()
                newPolyglot.locale(language.getLanguageCode())
                newPolyglot.extend(getJS(phrasePairs))

                setState {
                    polyglot = newPolyglot
                    isLoading = false
                }
            } catch (e: Exception) {
                console.error("Failed to fetch polyglot phrases", e)
                setState {
                    isLoading = false
                }
            }
        }
    }

    override fun RBuilder.render() {
        val setLanguage: (Language) -> Unit = { language ->
            if (language != state.selectedLanguage) {
                fetchPolyglot(language)
            }
        }

        val contextValue = js("{}")
            .unsafeCast<LocalizationContextValue>()
            .apply {
                this.polyglot = state.polyglot
                this.selectedLanguage = state.selectedLanguage
                this.setLanguage = setLanguage
                this.isLoading = state@isLoading
            }

        LocalizationContext.Provider {
            attrs.value = contextValue
            props.children?.let { +it }
        }
    }
}

fun RBuilder.localizationProvider(handler: LocalizationProviderProps.() -> Unit) {
    return child(LocalizationProvider::class) {
        this.attrs(handler)
    }
}
