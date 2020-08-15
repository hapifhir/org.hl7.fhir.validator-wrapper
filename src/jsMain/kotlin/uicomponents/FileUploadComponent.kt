package uicomponents

import css.FABStyle
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
external interface FileUploadProps : RProps {
    var onSelectFiles: (List<File>) -> Unit
}

class FileUploadState : RState {
    var files: List<File> = listOf()
}

class FileUploadComponent : RComponent<FileUploadProps, FileUploadState>() {

    init {
        state = FileUploadState()
    }

    override fun RBuilder.render() {

        fileListComponent {
            files = state.files
        }

        styledInput(InputType.file, name = "fileUpload", formEncType = InputFormEncType.multipartFormData) {
            css {
                display = Display.none
            }
            attrs {
                id = "FileUploadInput"
                multiple = true
                onInputFunction = { event ->
                    println("onInputFunction :: $event")
                    val input = document.getElementById("FileUploadInput") as HTMLInputElement
                    setState {
                        files = input.files?.asList()!!
                    }
                }
            }
        }

        styledButton {
            css {
                +FABStyle.fab
            }
            styledSvg {
                path {

                }

                inline fun RBuilder.styledSvg(content: String = "") = styledTag({ +content }) { SVG(emptyMap, it) }

                css {
                    setProp("fill", Color.white)
                    //backgroundColor = Color.blanchedAlmond
                }
                attrs {
                    src = "validate.svg"
                    tag()
                    set("fill", Color.white)
                }
            }
        }
        styledButton {
            +"Upload Files"
            attrs {
                name = "UploadFileButton"
                onClickFunction = {
                    val field = document.getElementById("FileUploadInput") as HTMLInputElement
                    field.click()
                }
            }
        }

    }
}

fun onFilesUpload(files: List<File>) {
    for (file in files) {
        println("${file.name}: ${file.type}")
        val reader = FileReader()
        reader.readAsText(file)
        reader.onloadend = {
            println(reader.result)
        }
    }
}

/**
 * We can use lambdas with receivers to make the component easier to work with.
 */
fun RBuilder.fileUploadComponent(handler: FileUploadProps.() -> Unit): ReactElement {
    return child(FileUploadComponent::class) {
        this.attrs(handler)
    }
}
