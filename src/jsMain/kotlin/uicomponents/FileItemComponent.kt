package uicomponents

import constants.MIMEType
import css.FileItemStyle
import kotlinx.css.*
import kotlinx.html.js.*
import react.*

import org.w3c.files.File
import org.w3c.files.FileReader
import react.RProps
import styled.*

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
                +FileItemStyle.listItem
            }
            styledImg {
                css {
                    +FileItemStyle.typeImage
                }
                attrs {
                    src = MIMEType.get(props.file.type)?.image ?: "images/upload.svg"
                }
                println(props.file.type)
            }
            styledP {
                +props.file?.name
                css {
                    +FileItemStyle.titleField
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
                    +FileItemStyle.typeImage
                }
                attrs {
                    src = "images/delete.svg"
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
