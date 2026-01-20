package ui.components.header.LanguageOption

import Polyglot
import context.LocalizationContext
import mui.material.*
import react.*
import csstype.*
import web.cssom.px
import model.ValidationContext
import mui.system.sx
import react.Props
import react.ReactNode
import utils.Language


external interface LanguageSelectProps : Props {}

class LanguageSelect(props : LanguageSelectProps) : RComponent<LanguageSelectProps, State>() {
    init {
    }
    override fun RBuilder.render() {
        LocalizationContext.Consumer { localizationContext ->
            context.ValidationContext.Consumer { validationContext ->
                // Extract values with null-safety defaults
                val polyglot = localizationContext?.polyglot ?: Polyglot()
                val selectedLanguage = localizationContext?.selectedLanguage ?: Language.ENGLISH
                val currentValidationContext = validationContext?.validationContext
                    ?: model.ValidationContext().setBaseEngine("DEFAULT")

                Box {
                    attrs{
                        sx{
                            minWidth = 120.px
                        }
                    }
                    FormControl {
                        attrs {
                            fullWidth = true
                            size = Size.small
                        }
                        InputLabel {
                            +polyglot.t("language")
                        }
                        Select {

                            attrs {
                                label = ReactNode("Language")
                                value = selectedLanguage.getLanguageCode().unsafeCast<Nothing?>()
                                onChange = { event, _ ->
                                    val selectedLang = Language.getSelectedLanguage(event.target.value)
                                    if (selectedLang != null) {
                                        // Call context to update language (handles polyglot fetching)
                                        localizationContext?.setLanguage?.invoke(selectedLang)
                                        // Update ValidationContext locale
                                        val updatedCtx = currentValidationContext.setLocale(selectedLang.getLanguageCode())
                                        validationContext?.updateValidationContext?.invoke(updatedCtx, false)
                                    }
                                }
                            }

                            MenuItem {
                                attrs {
                                    value = Language.ENGLISH.getLanguageCode()
                                }
                                +Language.ENGLISH.display
                            }
                            MenuItem {
                                attrs {
                                    value = Language.GERMAN.getLanguageCode()
                                }
                                +Language.GERMAN.display
                            }
                            MenuItem {
                                attrs {
                                    value = Language.SPANISH.getLanguageCode()
                                }
                                +Language.SPANISH.display
                            }
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.languageSelect(handler: LanguageSelectProps.() -> Unit) {
    return child(LanguageSelect::class) {
        this.attrs(handler)
    }
}

