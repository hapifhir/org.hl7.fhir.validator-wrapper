package ui.components

import css.FABStyle
import css.FileUploadStyle
import kotlinx.browser.document
import kotlinx.css.Display
import kotlinx.css.display
import kotlinx.css.svg
import kotlinx.html.InputFormEncType
import kotlinx.html.InputType
import kotlinx.html.SVG
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onInputFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.asList
import org.w3c.files.File
import react.*
import reactredux.containers.uploadFilesButton
import reactredux.containers.uploadFilesList
import styled.*

external interface FileUploadProps : RProps {
    var onValidate: (MutableList<File>) -> Unit
}

class FileUploadState : RState {
    var files: MutableList<File> = mutableListOf()
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
                            props.onValidate(state.files)
                        }
                    }
                }
            }

            uploadFilesButton { }
        }
    }
}

fun RBuilder.fileUploadComponent(handler: FileUploadProps.() -> Unit): ReactElement {
    return child(FileUploadComponent::class) {
        this.attrs(handler)
    }
}
