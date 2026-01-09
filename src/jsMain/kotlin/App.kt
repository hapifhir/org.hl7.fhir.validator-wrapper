import Polyglot
import api.getValidationPresets
import context.AppScreenContext
import context.LocalizationContext
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.css.*
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

val mainScope = MainScope()

class AppState : State {
    var presets: List<Preset> = emptyList()
    var validationContext: ValidationContext = ValidationContext().setBaseEngine("DEFAULT")
}

class App : RComponent<Props, AppState>() {

    init {
        state = AppState()

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

    private fun updateValidationContext(context: ValidationContext, resetBaseEngine: Boolean) {
        setState {
            validationContext = context
        }
    }

    override fun RBuilder.render() {
        AppScreenContext.Consumer { screenContext ->
            LocalizationContext.Consumer { localizationContext ->
                val appScreen = screenContext?.appScreen ?: AppScreen.VALIDATOR
                val polyglot = localizationContext?.polyglot ?: Polyglot()
                val selectedLanguage = localizationContext?.selectedLanguage ?: Language.ENGLISH

                styledDiv {
                    css {
                        +LandingPageStyle.mainDiv
                    }

                    child(Header::class) {
                        attrs.appScreen = appScreen
                        attrs.polyglot = polyglot
                        attrs.selectedLanguage = selectedLanguage
                        attrs.setScreen = {} // no-op (Header will use context directly)
                        attrs.fetchPolyglot = {} // no-op
                        attrs.setPolyglot = {} // no-op
                        attrs.setLanguage = {} // no-op
                        attrs.validationContext = state.validationContext
                        attrs.updateValidationContext = ::updateValidationContext
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
                                    majorText = polyglot.t("appscreen_validator_major")
                                    minorText = polyglot.t("appscreen_validator_minor")
                                }
                                tabLayout {
                                    this.polyglot = polyglot
                                }
                            }
                            AppScreen.SETTINGS -> {
                                sectionTitle {
                                    majorText = polyglot.t("appscreen_options_major")
                                    minorText = polyglot.t("appscreen_options_minor")
                                }
                                child(OptionsPage::class) {
                                    attrs.validationContext = state.validationContext
                                    attrs.igPackageInfoSet = emptySet()
                                    attrs.extensionSet = emptySet()
                                    attrs.profileSet = emptySet()
                                    attrs.bundleValidationRuleSet = emptySet()
                                    attrs.polyglot = polyglot
                                    attrs.updateValidationContext = { context -> updateValidationContext(context, false) }
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
                        this.polyglot = polyglot
                    }
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
