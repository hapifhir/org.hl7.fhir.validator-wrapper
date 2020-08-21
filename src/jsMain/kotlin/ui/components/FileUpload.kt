package ui.components

import css.FABStyle
import css.FileUploadStyle
import kotlinx.css.Display
import kotlinx.css.display
import kotlinx.html.InputFormEncType
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onInputFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.asList
import org.w3c.files.File
import react.*
import styled.css
import styled.styledDiv
import styled.styledImg
import styled.styledInput
import kotlin.browser.document

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
            fileListComponent {
                files = state.files
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

            styledInput(InputType.file, name = "fileUpload", formEncType = InputFormEncType.multipartFormData) {
                css {
                    // We don't display this object because it is
                    display = Display.none
                }
                attrs {
                    id = "FileUploadInput"
                    multiple = true
                    onInputFunction = { event ->
                        println("onInputFunction :: $event")
                        val input = document.getElementById("FileUploadInput") as HTMLInputElement
                        setState {
                            files.addAll(input.files?.asList()!!)
                        }
                    }
                }
            }
        }
    }
}

fun RBuilder.fileUploadComponent(handler: FileUploadProps.() -> Unit): ReactElement {
    return child(FileUploadComponent::class) {
        this.attrs(handler)
    }
}
