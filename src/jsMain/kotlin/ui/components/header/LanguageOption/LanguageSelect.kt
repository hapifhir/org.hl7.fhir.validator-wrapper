package ui.components.header.LanguageOption

import Polyglot
import mui.material.*
import react.*
import csstype.px
import model.CliContext
import mui.system.sx
import react.Props
import react.ReactNode
import utils.Language


external interface LanguageSelectProps : Props {
    var polyglot: Polyglot
    var fetchPolyglot: (String) -> Unit
    var selectedLanguage : Language
    var setLanguage: (Language) -> Unit
    var cliContext: CliContext
    var updateCliContext: (CliContext) -> Unit

}

class LanguageSelect(props : LanguageSelectProps) : RComponent<LanguageSelectProps, State>() {
    init {
    }
    override fun RBuilder.render() {
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
                    +props.polyglot.t("language")
                }
                Select {
                    attrs {
                        label = ReactNode("Language")
                        onChange = { event, _ ->
                            val selectedLanguage = Language.getSelectedLanguage(event.target.value)
                            if (selectedLanguage != null) {
                                props.setLanguage(selectedLanguage)
                                props.fetchPolyglot(selectedLanguage.getLanguageCode());
                                props.updateCliContext(props.cliContext.setLocale(selectedLanguage.getLanguageCode()))
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

fun RBuilder.languageSelect(handler: LanguageSelectProps.() -> Unit) {
    return child(LanguageSelect::class) {
        this.attrs(handler)
    }
}

