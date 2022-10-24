package ui.components.header.LanguageOption

import Polyglot
import mui.material.*
import react.*

import csstype.px
import mui.system.sx
import react.FC
import react.Props
import react.ReactNode
import react.useState

external interface LanguageSelectProps : Props {
    var polyglot: Polyglot
    var fetchPolyglot: (String) -> Unit
    var language : String
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
                    +"Language"
                }
                Select {

                    attrs {
                        label = ReactNode("Language")
                        onChange = { event, _ ->
                            //props.language = event.target.value
                            console.log(event.target.value)
                            props.fetchPolyglot(event.target.value)
                        }
                    }

                    MenuItem {
                        attrs {
                            value = "en"
                        }
                        +"English"
                    }
                    MenuItem {
                        attrs {
                            value = "es"
                        }
                        +"Español" // Spanish
                    }
                    MenuItem {
                        attrs {
                            value = "de"
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
