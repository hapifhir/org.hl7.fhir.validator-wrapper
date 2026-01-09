import Polyglot
import api.getPolyglotPhrases
import api.getValidationPresets
import context.AppScreenContext
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.css.*
import kotlinx.serialization.json.JsonPrimitive
import model.AppScreen
import model.Preset
import model.ValidationContext
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import ui.components.footer.footer
import ui.components.header.Header
import ui.components.header.HeaderStyle
import ui.components.main.sectionTitle
import ui.components.options.OptionsPage
import ui.components.tabs.tabLayout
import utils.Language
import utils.getJS

val mainScope = MainScope()

class AppState : State {
    var polyglot: Polyglot = Polyglot()
    var presets: List<Preset> = emptyList()
}

class App : RComponent<Props, AppState>() {

    init {
        state = AppState()

        // Fetch polyglot on mount (English only)
        mainScope.launch {
            try {
                val phrases = getPolyglotPhrases("en")
                val phrasePairs: Array<Pair<String, Any?>> = phrases.entries.map {
                    Pair(it.key, (it.value as JsonPrimitive).content)
                }.toTypedArray()

                val polyglotInstance = Polyglot()
                polyglotInstance.locale("en")
                polyglotInstance.extend(getJS(phrasePairs))
                setState {
                    polyglot = polyglotInstance
                }
            } catch (e: Exception) {
                console.error("Failed to fetch polyglot phrases", e)
            }
        }

        // Fetch presets on mount
        mainScope.launch {
            try {
                val fetchedPresets = getValidationPresets()
                setState {
                    presets = fetchedPresets
                }
            } catch (e: Exception) {
                console.error("Failed to fetch presets", e)
            }
        }
    }

    override fun RBuilder.render() {
        AppScreenContext.Consumer { contextValue ->
            val appScreen = contextValue?.appScreen ?: AppScreen.VALIDATOR

            styledDiv {
                css {
                    +LandingPageStyle.mainDiv
                }

                child(Header::class) {
                    attrs.appScreen = appScreen
                    attrs.polyglot = state.polyglot
                    attrs.selectedLanguage = Language.ENGLISH
                    attrs.setScreen = {} // no-op (Header will use context directly)
                    attrs.fetchPolyglot = {} // no-op
                    attrs.setPolyglot = {} // no-op
                    attrs.setLanguage = {} // no-op
                    attrs.validationContext = ValidationContext().setBaseEngine("DEFAULT")
                    attrs.updateValidationContext = { _, _ -> } // no-op
                }

                styledDiv {
                    css {
                        paddingTop = HeaderStyle.HEADER_HEIGHT
                        display = Display.flex
                        flexDirection = FlexDirection.column
                        flex(flexGrow = 1.0, flexShrink = 1.0, flexBasis = FlexBasis.auto)
                    }
                    when (appScreen) {
                        AppScreen.VALIDATOR -> {
                            sectionTitle {
                                majorText = state.polyglot.t("appscreen_validator_major")
                                minorText = state.polyglot.t("appscreen_validator_minor")
                            }
                            tabLayout {
                                polyglot = state.polyglot
                            }
                        }
                        AppScreen.SETTINGS -> {
                            sectionTitle {
                                majorText = state.polyglot.t("appscreen_options_major")
                                minorText = state.polyglot.t("appscreen_options_minor")
                            }
                            child(OptionsPage::class) {
                                attrs.validationContext = ValidationContext().setBaseEngine("DEFAULT")
                                attrs.igPackageInfoSet = emptySet()
                                attrs.extensionSet = emptySet()
                                attrs.profileSet = emptySet()
                                attrs.bundleValidationRuleSet = emptySet()
                                attrs.polyglot = state.polyglot
                                attrs.updateValidationContext = {} // no-op
                                attrs.updateIgPackageInfoSet = {} // no-op
                                attrs.updateExtensionSet = {} // no-op
                                attrs.setSessionId = {} // no-op
                                attrs.updateProfileSet = {} // no-op
                                attrs.updateBundleValidationRuleSet = {} // no-op
                            }
                        }
                    }
                }
                footer {
                    polyglot = state.polyglot
                }
            }
        }
    }
}

/**
 * CSS
 */
object LandingPageStyle : StyleSheet("LandingPageStyle", isStatic = true) {
    val mainDiv by css {
        display = Display.flex
        flexDirection = FlexDirection.column
        height = 100.vh
    }
}
