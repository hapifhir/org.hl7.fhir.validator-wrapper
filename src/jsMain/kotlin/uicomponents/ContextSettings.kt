package uicomponents

import kotlinx.html.InputType
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.*

import kotlinx.html.id
import model.CliContext
import org.w3c.dom.HTMLTextAreaElement
import react.RProps
import react.dom.textArea
import kotlin.browser.document

/**
 * We need a way to provide a callback to the main page using this component. This can be done, through React props.
 * In this case, we define the callback for the Submit funtionality here.
 */
external interface ContextSettingsProps : RProps {
    var onSave: (CliContext) -> Unit
    var cliContext: CliContext
}

class ContextSettings : RComponent<ContextSettingsProps, RState>() {
    override fun RBuilder.render() {
        h2 {
            + "CliContext Settings"
        }
        ul {
            li {
                input(type = InputType.checkBox, name = "doNative") {
                    attrs {
                        id = "doNative"
                    }
                }
                span {
                    + "doNative"
                }
            }
            li {
                input(type = InputType.checkBox, name = "hintAboutNonMustSupport") {
                    attrs {
                        id = "hintAboutNonMustSupport"
                    }
                }
                span {
                    + "hintAboutNonMustSupport"
                }
            }
            li {
                input(type = InputType.checkBox, name = "assumeValidRestReferences") {
                    attrs {
                        id = "assumeValidRestReferences"
                    }
                }
                span {
                    + "assumeValidRestReferences"
                }
            }
            li {
                input(type = InputType.checkBox, name = "noExtensibleBindingMessages") {
                    attrs {
                        id = "noExtensibleBindingMessages"
                    }
                }
                span {
                    + "noExtensibleBindingMessages"
                }
            }
            li {
                input(type = InputType.checkBox, name = "securityChecks") {
                    attrs {
                        id = "securityChecks"
                    }
                }
                span {
                    + "securityChecks"
                }
            }
            li {
                input(type = InputType.checkBox, name = "showTimes") {
                    attrs {
                        id = "showTimes"
                    }
                }
                span {
                    + "showTimes"
                }
            }
        }
    }
}

/**
 * We can use lambdas with receivers to make the component easier to work with.
 */
fun RBuilder.contextSettings(handler: ContextSettingsProps.() -> Unit): ReactElement {
    return child(ContextSettings::class) {
        this.attrs(handler)
    }
}
