package ui.components.header.LanguageOption

import Polyglot
import mui.material.*
import react.*
import csstype.px
import mui.system.sx
import react.Props
import react.ReactNode
import utils.Language

external interface LanguageSelectProps : Props {
    var polyglot: Polyglot
    var fetchPolyglot: (String) -> Unit
    var selectedLanguage : Language
    var setLanguage: (Language) -> Unit
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
                            console.log(event.target.value)
                            for (language in Language.values()) {
                                if (event.target.value == language.code.substring(0, 2)) {
                                    props.setLanguage(language)
                                    props.fetchPolyglot(language.code.substring(0, 2));
                                    break
                                }
                            }
                        }
                    }

                    MenuItem {
                        attrs {
                            value = Language.ENGLISH.code.substring(0, 2)
                        }
                        +"English"
                    }
                    MenuItem {
                        attrs {
                            value = Language.GERMAN.code.substring(0, 2)
                        }
                        +"Deutsch" // German
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

