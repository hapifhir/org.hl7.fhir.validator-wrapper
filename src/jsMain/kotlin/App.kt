import Polyglot
import context.AppScreenContext
import context.LocalizationContext
import kotlinx.coroutines.MainScope
import kotlinx.css.*
import model.AppScreen
import model.ValidationContext
import react.*
import styled.StyleSheet
import styled.css
import styled.styledDiv
import ui.components.footer.footer
import ui.components.header.Header
import ui.components.header.HeaderStyle
import ui.components.header.header
import ui.components.main.sectionTitle
import ui.components.options.OptionsPage
import ui.components.tabs.tabLayout
import utils.Language

val mainScope = MainScope()

class App : RComponent<Props, State>() {

    override fun RBuilder.render() {
        AppScreenContext.Consumer { screenContext ->
            LocalizationContext.Consumer { localizationContext ->
                context.ValidationContext.Consumer { validationContext ->
                val appScreen = screenContext?.appScreen ?: AppScreen.VALIDATOR
                val polyglot = localizationContext?.polyglot ?: Polyglot()
                val selectedLanguage = localizationContext?.selectedLanguage ?: Language.ENGLISH

                styledDiv {
                    css {
                        +LandingPageStyle.mainDiv
                    }

                    header {
                        // No attrs needed - component consumes contexts directly
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
                                    attrs.validationContext = validationContext?.validationContext
                                        ?: ValidationContext().setBaseEngine("DEFAULT")
                                    attrs.igPackageInfoSet = validationContext?.igPackageInfoSet ?: emptySet()
                                    attrs.extensionSet = validationContext?.extensionSet ?: emptySet()
                                    attrs.profileSet = validationContext?.profileSet ?: emptySet()
                                    attrs.bundleValidationRuleSet = validationContext?.bundleValidationRuleSet ?: emptySet()
                                    attrs.polyglot = polyglot
                                    attrs.updateValidationContext = { context, resetBaseEngine ->
                                        validationContext?.updateValidationContext?.invoke(context, resetBaseEngine)
                                    }
                                    attrs.updateIgPackageInfoSet = { set, resetBaseEngine ->
                                        validationContext?.updateIgPackageInfoSet?.invoke(set, resetBaseEngine)
                                    }
                                    attrs.updateExtensionSet = { set, resetBaseEngine ->
                                        validationContext?.updateExtensionSet?.invoke(set, resetBaseEngine)
                                    }
                                    attrs.setSessionId = { id ->
                                        validationContext?.setSessionId?.invoke(id)
                                    }
                                    attrs.updateProfileSet = { set, resetBaseEngine ->
                                        validationContext?.updateProfileSet?.invoke(set, resetBaseEngine)
                                    }
                                    attrs.updateBundleValidationRuleSet = { set, resetBaseEngine ->
                                        validationContext?.updateBundleValidationRuleSet?.invoke(set, resetBaseEngine)
                                    }
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
