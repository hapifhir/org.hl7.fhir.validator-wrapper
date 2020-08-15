package uicomponents

import css.FileInfoStyle
import io.ktor.utils.io.charsets.Charset
import io.ktor.utils.io.charsets.Charsets
import io.ktor.utils.io.core.toByteArray
import kotlinx.css.*
import kotlinx.html.*
import kotlinx.html.attributes.enumEncode
import kotlinx.html.js.*
import react.*
import react.dom.*

import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLTextAreaElement
import org.w3c.dom.asList
import org.w3c.files.File
import org.w3c.files.FileReader
import react.RProps
import react.dom.textArea
import styled.*
import kotlin.browser.document

/**
 * We need a way to provide a callback to the main page using this component. This can be done, through React props.
 * In this case, we define the callback for the Submit funtionality here.
 */
external interface FileItemProps : RProps {
    var file: File
}

class FileItemState : RState {
    var summaryActive: Boolean = false
}

class FileItemComponent : RComponent<FileItemProps, FileItemState>() {
    override fun RBuilder.render() {
        styledDiv {  }
        styledLi {
            css {
                +FileInfoStyle.listItem
            }
            attrs {
                onClickFunction = {
                    setState {
                        summaryActive = true
                    }
                }
            }
            styledImg {
                css {
                    +FileInfoStyle.typeImage
                }
                attrs {
                    src = "json-svgrepo-com.svg"
                }
            }
            styledP {
                +"Test file title"
                css {
                    width = 100.pct
                }
            }
            styledImg {
                css {
                    +FileInfoStyle.typeImage
                }
                attrs {
                    src = "json-svgrepo-com.svg"
                }
            }
        }
        fileSummaryComponent {
            active = state.summaryActive
            file = props.file
            onClose = {
                setState {
                    summaryActive = false
                }
            }
        }

    }
}

/**
 * We can use lambdas with receivers to make the component easier to work with.
 */
fun RBuilder.fileItemComponent(handler: FileItemProps.() -> Unit): ReactElement {
    return child(FileItemComponent::class) {
        this.attrs(handler)
    }
}
