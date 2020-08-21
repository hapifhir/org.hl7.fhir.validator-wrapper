package ui.components

import constants.MIMEType
import css.FileItemStyle
import css.TextStyle
import kotlinx.html.js.onClickFunction
import org.w3c.files.File
import react.*
import styled.css
import styled.styledImg
import styled.styledLi
import styled.styledP
import utils.FileEventListener
import utils.parseFile

external interface FileItemProps : RProps {
    var file: File
    var onDelete: (File) -> Unit
}

class FileItemState : RState {
    var summaryActive: Boolean = false
    var fileContent: String = ""
}

class FileItemComponent : RComponent<FileItemProps, FileItemState>(), FileEventListener {
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
                    +TextStyle.h3
                }
                attrs {
                    onClickFunction = {
                        load(props.file)
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

    private fun load(file: File) {
        parseFile(props.file, this)
    }

    override fun onLoadStart(file: File) {}

    override fun onLoadProgress(file: File, loaded: Double, total: Double) {}

    override fun onLoadComplete(file: File, result: String) {
        setState {
            fileContent = result
        }
    }

    override fun onLoadError(file: File, error: Any?, message: String) {}
}

fun RBuilder.fileItemComponent(handler: FileItemProps.() -> Unit): ReactElement {
    return child(FileItemComponent::class) {
        this.attrs(handler)
    }
}
