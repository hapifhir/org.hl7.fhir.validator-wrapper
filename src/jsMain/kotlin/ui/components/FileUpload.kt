package ui.components

import css.tabs.uploadtab.FileUploadStyle
import css.widget.FABStyle
import kotlinx.browser.document
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import react.*
import reactredux.containers.uploadFilesButton
import reactredux.containers.uploadFilesList
import reactredux.containers.validateFilesButton
import styled.css
import styled.styledDiv
import styled.styledImg

external interface FileUploadProps : RProps {
}

class FileUploadState : RState {
}

class FileUploadComponent : RComponent<FileUploadProps, FileUploadState>() {

    init {
        state = FileUploadState()
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                +FileUploadStyle.layout
            }
            uploadFilesList { }
            styledDiv {
                css {
                    +FABStyle.endButtonContainer
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
                                src = "images/upload.svg"

                            }
                        }
                        attrs {
                            onClickFunction = {
                                val field = document.getElementById("FileUploadInput") as HTMLInputElement
                                field.click()
                            }
                        }
                    }
                    validateFilesButton { }
                }
                uploadFilesButton { }
            }
        }
    }
}

fun RBuilder.fileUploadComponent(handler: FileUploadProps.() -> Unit): ReactElement {
    return child(FileUploadComponent::class) {
        this.attrs(handler)
    }
}
