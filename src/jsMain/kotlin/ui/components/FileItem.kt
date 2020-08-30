package ui.components

import constants.MIMEType
import css.FileItemStyle
import css.TextStyle
import kotlinx.html.js.onClickFunction
import model.FileInfo
import react.*
import styled.css
import styled.styledImg
import styled.styledLi
import styled.styledP

external interface FileItemProps : RProps {
    var fileInfo: FileInfo
    var onDelete: (FileInfo) -> Unit
}

class FileItemState : RState {
    var summaryActive: Boolean = false
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
                    src = MIMEType.get(props.fileInfo.fileType)?.image ?: "images/upload.svg"
                }
                println(props.fileInfo.fileType)
            }
            styledP {
                +props.fileInfo.fileName
                css {
                    +FileItemStyle.titleField
                    +TextStyle.h3
                }
                attrs {
                    onClickFunction = {
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
                            props.onDelete(props.fileInfo)
                        }
                    }
                }
            }
        }
        fileSummaryComponent {
            active = state.summaryActive
            fileName = props.fileInfo.fileName
            fileContent = props.fileInfo.fileContent
            onClose = {
                setState {
                    summaryActive = false
                }
            }
        }
    }
}

fun RBuilder.fileItemComponent(handler: FileItemProps.() -> Unit): ReactElement {
    return child(FileItemComponent::class) {
        this.attrs(handler)
    }
}
