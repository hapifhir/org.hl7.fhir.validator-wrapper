package ui.components

import kotlinx.browser.document
import kotlinx.css.Display
import kotlinx.css.display
import kotlinx.html.InputFormEncType
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onInputFunction
import model.FileInfo
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.asList
import org.w3c.files.File
import react.*
import styled.css
import styled.styledInput
import utils.fileutils.FileLoadEventListener
import utils.fileutils.FileLoadState
import utils.fileutils.loadFile

interface UploadFilesButtonProps : RProps {
    var uploadFile: (FileInfo) -> Unit
//    var toggleUploadInProgress: (Boolean) -> Unit
}

class UploadFilesButtonState : RState {
    var filesCurrentlyValidating: Int = 0
}

class UploadFilesButton(props: UploadFilesButtonProps) : RComponent<UploadFilesButtonProps,
        UploadFilesButtonState>(props),
    FileLoadEventListener {

    init {
        state = UploadFilesButtonState()
    }

    override fun RBuilder.render() {
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
//                    props.toggleUploadInProgress(true)
                    val files = input.files?.asList()
                    setState {
                        filesCurrentlyValidating = files?.size!!
                    }
                    files?.forEach {
                        startLoadingFile(it)
                    }
                }
            }
        }
    }

    private fun startLoadingFile(file: File) {
        loadFile(file, this)
    }

    override fun onLoadStart(fileLoadState: FileLoadState) {}

    override fun onLoadProgress(fileLoadState: FileLoadState) {}

    override fun onLoadComplete(fileLoadState: FileLoadState) {
        setState {
            filesCurrentlyValidating--
        }
        val f = FileLoadState.toFileInfo(fileLoadState)
        println("name: ${f.fileName} \ncontent: ${f.fileContent}")
        props.uploadFile(FileLoadState.toFileInfo(fileLoadState))
        checkValidationQueue()
    }

    override fun onLoadError(fileLoadState: FileLoadState) {
        // TODO deal with this eventually
    }

    private fun checkValidationQueue() {
        if (state.filesCurrentlyValidating == 0) {
//            props.toggleUploadInProgress(false)
        }
    }
}