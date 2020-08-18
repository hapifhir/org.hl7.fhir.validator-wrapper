package uicomponents

import css.FABStyle
import css.FileListStyle
import css.FileUploadStyle
import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.*

import kotlinx.html.id
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLTextAreaElement
import react.RProps
import react.dom.textArea
import styled.css
import styled.styledDiv
import styled.styledImg
import styled.styledTextArea
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
        styledDiv {
            css {
                position = Position.relative
                display = Display.flex
                flex(flexBasis = 100.pct)
            }
            // TODO fix flex layout and css
            styledTextArea {
                css {
                    overflow = Overflow.auto
                    wordWrap = WordWrap.unset
                    resize = Resize.none
                    flex(flexBasis = 100.pct)
                    padding(12.px)
                }
                attrs {
                    placeholder = "Enter Resoure Manually"
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

/**
 * We can use lambdas with receivers to make the component easier to work with.
 */
fun RBuilder.resourceEntryComponent(handler: ResourceEntryProps.() -> Unit): ReactElement {
    return child(ResourceEntryComponent::class) {
        this.attrs(handler)
    }
}
