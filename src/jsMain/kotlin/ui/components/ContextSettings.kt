package ui.components

import kotlinx.html.InputType
import kotlinx.html.id
import model.CliContext
import react.*
import react.dom.*

external interface ContextSettingsProps : RProps {
    var onSave: (CliContext) -> Unit
    var cliContext: CliContext
}

class ContextSettings : RComponent<ContextSettingsProps, RState>() {
    override fun RBuilder.render() {
        h2 {
            +"CliContext Settings"
        }
        checkboxInput {
            settingName = "Mark Iantorno"
            settingDescription = "Mark is a senior developer at SmileCDR"
            onChange = { println(it) }
        }
        ul {
            li {
                input(type = InputType.checkBox, name = "doNative") {
                    attrs {
                        id = "doNative"
                    }
                }
                span {
                    +"doNative"
                }
            }
            li {
                input(type = InputType.checkBox, name = "hintAboutNonMustSupport") {
                    attrs {
                        id = "hintAboutNonMustSupport"
                    }
                }
                span {
                    +"hintAboutNonMustSupport"
                }
            }
            li {
                input(type = InputType.checkBox, name = "assumeValidRestReferences") {
                    attrs {
                        id = "assumeValidRestReferences"
                    }
                }
                span {
                    +"assumeValidRestReferences"
                }
            }
            li {
                input(type = InputType.checkBox, name = "noExtensibleBindingMessages") {
                    attrs {
                        id = "noExtensibleBindingMessages"
                    }
                }
                span {
                    +"noExtensibleBindingMessages"
                }
            }
            li {
                input(type = InputType.checkBox, name = "securityChecks") {
                    attrs {
                        id = "securityChecks"
                    }
                }
                span {
                    +"securityChecks"
                }
            }
            li {
                input(type = InputType.checkBox, name = "showTimes") {
                    attrs {
                        id = "showTimes"
                    }
                }
                span {
                    +"showTimes"
                }
            }
        }
    }
}

fun RBuilder.contextSettings(handler: ContextSettingsProps.() -> Unit): ReactElement {
    return child(ContextSettings::class) {
        this.attrs(handler)
    }
}
