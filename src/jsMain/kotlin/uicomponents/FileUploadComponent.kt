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
                position = Position.relative
                display = Display.flex
                flex(flexBasis = 100.pct)
            }

            fileListComponent {
                files = state.files
            }

            styledDiv {
                css {
                    display = Display.flex
                    flexDirection = FlexDirection.column
                    position = Position.absolute
                    right = 0.px
                    bottom = 0.px
                    margin(24.px)
                }
                styledDiv {
                    css {
                        +FABStyle.fab
                        marginBottom = 12.px
                        display = Display.flex
                        justifyContent = JustifyContent.center
                    }
                    styledImg {
                        css {
                            setProp("fill", Color.white)
                        }
                        attrs {
                            src = "upload.svg"
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
                        display = Display.flex
                        justifyContent = JustifyContent.center
                    }
                    styledImg {
                        css {
                            setProp("fill", Color.white)
                        }
                        attrs {
                            src = "validate.svg"
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
