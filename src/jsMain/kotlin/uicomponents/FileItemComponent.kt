package uicomponents

import constants.MIMEType
import constants.MIMEType.Companion.get
import css.FileInfoStyle
import css.FileSummaryStyle
import css.HL7_RED
import css.NOT_BLACK
import io.ktor.utils.io.charsets.Charset
import io.ktor.utils.io.charsets.Charsets
import io.ktor.utils.io.core.toByteArray
import kotlinx.css.*
import kotlinx.css.properties.border
import kotlinx.css.properties.borderBottom
import kotlinx.html.*
import kotlinx.html.attributes.enumEncode
import kotlinx.html.js.*
import react.*
import react.dom.*

import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLTextAreaElement
import org.w3c.dom.MimeType
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
    var onDelete: (File) -> Unit
}

class FileItemState : RState {
    var summaryActive: Boolean = false
    var fileContent: String = ""
}

class FileItemComponent : RComponent<FileItemProps, FileItemState>() {
    override fun RBuilder.render() {
        styledLi {
            css {
//                +FileInfoStyle.listItem
                display = Display.flex
                margin(4.px)
                flex(flexBasis = 100.pct)
                flexDirection = FlexDirection.row
                justifyContent = JustifyContent.flexStart
                alignItems = Align.center
            }
            styledImg {
                css {
                    padding(8.px)
                    +FileInfoStyle.typeImage

                }
                attrs {
                    src = MIMEType.get(props.file.type)?.image ?: "upload.svg"
                }
                println(props.file.type)
            }
            styledP {
                +props.file?.name
                css {
                    flexGrow = 1.0
                }
                attrs {
                    onClickFunction = {
                        readFile(props.file)
                        setState {
                            summaryActive = true
                        }
                    }
                }
            }
            styledImg {
                css {
                    padding(8.px)
                    +FileInfoStyle.typeImage
                }
                attrs {
                    src = "delete.svg"
                    onClickFunction = {
                        setState {
                            props.onDelete(props.file)
                        }
                    }
                }
            }
        }
        fileSummaryComponent {
            active = state.summaryActive
            fileName = props.file.name
            fileContent = state.fileContent
            onClose = {
                setState {
                    summaryActive = false
                }
            }
        }
    }
    fun readFile(file: File?) {
        val reader = FileReader()
        if (file != null) {
            println("reading file ${file.name}")
            reader.readAsText(file)
            reader.onloadend = {
                setState {
                    fileContent = reader.result
                }
            }
        } else {
            println("null file")
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
