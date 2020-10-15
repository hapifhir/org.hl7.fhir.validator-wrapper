package ui.components

import kotlinx.browser.document
import kotlinx.css.Display
import kotlinx.css.display
import kotlinx.html.InputFormEncType
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import model.FileInfo
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.asList
import org.w3c.dom.events.Event
import org.w3c.files.File
import react.*
import styled.css
import styled.styledInput
import utils.fileutils.FileLoadEventListener
import utils.fileutils.FileLoadState
import utils.fileutils.loadFile

interface UploadFilesButtonProps : RProps {
    var uploadFile: (FileInfo) -> Unit
}

class UploadFilesButtonState : RState {
    var filesCurrentlyValidating: Int = 0
}

class UploadFilesButton : RComponent<UploadFilesButtonProps, UploadFilesButtonState>(), FileLoadEventListener {

    init {
        state = UploadFilesButtonState()
    }

    override fun RBuilder.render() {
        styledInput(InputType.file, name = "fileUpload", formEncType = InputFormEncType.multipartFormData) {
            css {
                // We don't display this object
                display = Display.none
            }
            attrs {
                id = "FileUploadInput"
                multiple = true
                onChangeFunction = {
                    val input = document.getElementById("FileUploadInput") as HTMLInputElement
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

    override fun onLoadStart(fileLoadState: FileLoadState) {
    }

    override fun onLoadProgress(fileLoadState: FileLoadState) {
    }

    override fun onLoadComplete(event: Event, fileLoadState: FileLoadState) {
        setState {
            filesCurrentlyValidating--
        }
        props.uploadFile(FileInfo(fileName = fileLoadState.file.name,
            fileContent = fileLoadState.content,
            fileType = fileLoadState.file.extension))
    }

    override fun onLoadError(fileLoadState: FileLoadState) {
        // TODO deal with this eventually
    }

    override fun onAbort(fileLoadState: FileLoadState) {
    }

    override fun onLoadEnd(event: Event, fileLoadState: FileLoadState) {
    }
}

val File.extension: String get() = name.substringAfterLast('.', "")