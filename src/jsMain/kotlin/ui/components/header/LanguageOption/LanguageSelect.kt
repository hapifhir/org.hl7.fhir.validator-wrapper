package ui.components.header.LanguageOption

import Polyglot
import mui.material.*
import react.*

external interface LanguageSelectProps : Props {
    var polyglot: Polyglot

    var fetchPolyglot: (String) -> Unit
}

class LanguageSelect(props : LanguageSelectProps) : RComponent<LanguageSelectProps, State>() {
    init {
        var language = "Language"
    }
    override fun RBuilder.render() {
        Box {
            /*
            sx {
                minWidth = 120.px
            }
             */
            FormControl {
                //fullWidth = true
                //size = Size.small
                InputLabel {
                    +"Language"
                }
                Select {
                    //label = ReactNode("Language")
                    /*
                    onChange = { event, _ ->
                        language = event.target.value
                    }
                     */
                    MenuItem {
                        //value = "en"
                        +"English"
                    }
                    MenuItem {
                        //value = "es"
                        +"Spanish"
                    }
                    MenuItem {
                        //value = "30"
                        +"German"
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


/*
val languageSelect = FC<Props> {
    var language by useState("Language")
    Box {
        sx {
            minWidth = 120.px
        }
        FormControl {
            fullWidth = true
            size = Size.small
            InputLabel {
                //id = "demo-simple-select-label"
                +"Language"
            }
            Select {
                //labelId = "demo-simple-select-label"
                //id = "demo-simple-select"
                //value = age
                label = ReactNode("Language")
                onChange = { event, _ ->
                    language = event.target.value
                }
                MenuItem {
                    value = "en"
                    +"English"
                }
                MenuItem {
                    value = "es"
                    +"Spanish"
                }
                MenuItem {
                    value = "30"
                    +"German"
                }
            }
        }
    }
}
 */
