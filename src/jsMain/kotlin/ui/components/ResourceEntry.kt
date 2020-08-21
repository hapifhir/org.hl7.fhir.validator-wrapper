package ui.components

import css.FABStyle
import css.FileUploadStyle
import css.ManualEntryStyle
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLTextAreaElement
import react.*
import styled.css
import styled.styledDiv
import styled.styledImg
import styled.styledTextArea
import kotlin.browser.document

external interface ResourceEntryProps : RProps {
    var onSubmit: (String) -> Unit
}

class ResourceEntryComponent : RComponent<ResourceEntryProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                +ManualEntryStyle.entryContainer
            }
            // TODO fix flex layout and css
            styledTextArea {
                css {
                    +ManualEntryStyle.entryField
                }
                attrs {
                    id = "inputTextArea"
                    placeholder = "Enter Resource Manually"
                }
            }
            styledDiv {
                css {
                    +FileUploadStyle.buttonContainer
                }
                styledDiv {
                    css {
                        +FABStyle.fab
                    }
                    styledImg {
                        css {

                        }
                        attrs {
                            src = "images/validate.svg"
                        }
                    }
                    attrs {
                        onClickFunction = {
                            val field = document.getElementById("inputTextArea") as HTMLTextAreaElement
                            props.onSubmit(field.value)
                            println(field.value)
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.resourceEntryComponent(handler: ResourceEntryProps.() -> Unit): ReactElement {
    return child(ResourceEntryComponent::class) {
        this.attrs(handler)
    }
}
