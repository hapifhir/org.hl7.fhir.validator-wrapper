package uicomponents

import kotlinx.html.js.onClickFunction
import react.*
import react.dom.*

import kotlinx.html.id
import org.w3c.dom.HTMLTextAreaElement
import react.RProps
import react.dom.textArea
import kotlin.browser.document

/**
 * We need a way to provide a callback to the main page using this component. This can be done, through React props.
 * In this case, we define the callback for the Submit funtionality here.
 */
external interface ResourceEntryProps : RProps {
    var onSubmit: (String) -> Unit
}

class ResourceEntryComponent : RComponent<ResourceEntryProps, RState>() {
    override fun RBuilder.render() {
        textArea {
            // TODO css
            attrs {
                id = "inputTextArea"
                cols = "60"
                rows = "30"
                placeholder = "Enter Resoure Manually"
            }
        }
        button {
            +"Validate"
            attrs {
                name = "ValidateButton"
                onClickFunction = {
                    val field = document.getElementById("inputTextArea") as HTMLTextAreaElement
                    props.onSubmit(field.value)
                    //println(field.value)
                }
            }
        }
    }
}

/**
 * We can use lambdas with receivers to make the component easier to work with.
 */
fun RBuilder.resourceEntryComponent(handler: ResourceEntryProps.() -> Unit): ReactElement {
    return child(ResourceEntryComponent::class) {
        this.attrs(handler)
    }
}
