package ui.components.tabs.uploadtab

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

const val FILE_UPLOAD_ELEMENT_ID = "FileUploadInput"

interface UploadFilesComponentProps : RProps {
    var onFileUpload: (FileInfo) -> Unit
}

/**
 * A non-displayed component for uploading files from the local machine.
 */
class UploadFilesComponent : RComponent<UploadFilesComponentProps, RState>(), FileLoadEventListener {

    override fun RBuilder.render() {
        styledInput(InputType.file, name = "fileUpload", formEncType = InputFormEncType.multipartFormData) {
            css {
                // We don't display this object
                display = Display.none
            }
            attrs {
                id = FILE_UPLOAD_ELEMENT_ID
                multiple = true
                onChangeFunction = {
                    val input = document.getElementById("FileUploadInput") as HTMLInputElement
                    val files = input.files?.asList()
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

    override fun onLoadComplete(event: Event, fileLoadState: FileLoadState) {
        props.onFileUpload(FileInfo(fileName = fileLoadState.file.name,
            fileContent = fileLoadState.content,
            fileType = fileLoadState.file.extension))
    }

    override fun onLoadError(fileLoadState: FileLoadState) {
        println("Error loading ${fileLoadState.file.name}!")
        // TODO deal with this
    }

    override fun onLoadStart(fileLoadState: FileLoadState) {}
    override fun onLoadProgress(fileLoadState: FileLoadState) {}
    override fun onAbort(fileLoadState: FileLoadState) {}
    override fun onLoadEnd(event: Event, fileLoadState: FileLoadState) {}
}

/**
 * React Component Builder
 */
fun RBuilder.uploadFilesComponent(handler: UploadFilesComponentProps.() -> Unit): ReactElement {
    return child(UploadFilesComponent::class) {
        this.attrs(handler)
    }
}

val File.extension: String get() = name.substringAfterLast('.', "")