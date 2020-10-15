package ui.components

import api.sendDebugMessage
import kotlinx.browser.document
import kotlinx.coroutines.launch
import kotlinx.css.Display
import kotlinx.css.display
import kotlinx.html.InputFormEncType
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onFormInputFunction
import kotlinx.html.js.onInputFunction
import kotlinx.html.js.onSubmitFunction
import mainScope
import model.FileInfo
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.asList
import org.w3c.dom.events.Event
import org.w3c.files.File
import org.w3c.xhr.ProgressEvent
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
}

var filesCurrentlyValidating: Int = 0

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
                onSubmitFunction = {
                    mainScope.launch {
                        sendDebugMessage("submit received")
                    }
                }
                onChangeFunction = {
                    mainScope.launch {
                        sendDebugMessage("change received")
                    }
                    val input = document.getElementById("FileUploadInput") as HTMLInputElement
                    val files = input.files?.asList()
                    mainScope.launch {
                        sendDebugMessage("Received the following files::")
                        files?.forEach { sendDebugMessage(it.name) }
                    }
                    setState {
                        filesCurrentlyValidating = files?.size!!
                    }
                    files?.forEach {
                        startLoadingFile(it)
                    }
                }
                onFormInputFunction = {
                    mainScope.launch {
                        sendDebugMessage("form input received")
                    }
                }
                onInputFunction = {
                    mainScope.launch {
                        sendDebugMessage("input received")
                    }
                }
            }
        }
    }

    private fun startLoadingFile(file: File) {
        mainScope.launch {
            sendDebugMessage("startLoadingFile ${file.name}")
        }
        loadFile(file, this)
    }

    override fun onLoadStart(fileLoadState: FileLoadState) {
        mainScope.launch {
            sendDebugMessage("onLoadStart")
        }
    }

    override fun onLoadProgress(fileLoadState: FileLoadState) {
        mainScope.launch {
            sendDebugMessage("onLoadProgress")
        }
    }

    override fun onLoadComplete(event: Event, fileLoadState: FileLoadState) {
        mainScope.launch {
            sendDebugMessage("onLoadComplete")
            sendDebugMessage("filename ${fileLoadState.file.name}")
            sendDebugMessage("error ${fileLoadState.error}")
            sendDebugMessage("message ${fileLoadState.errorMessage}")
            sendDebugMessage("content ${fileLoadState.content}")
            event as ProgressEvent
            sendDebugMessage("${event.type} - ${event.loaded} / ${event.total}")
        }
        setState {
            filesCurrentlyValidating--
        }
        val f = FileLoadState.toFileInfo(fileLoadState)
        //println("name: ${f.fileName} \ncontent: ${f.fileContent}")
        props.uploadFile(FileLoadState.toFileInfo(fileLoadState))
    }

    override fun onLoadError(fileLoadState: FileLoadState) {
        mainScope.launch {
            sendDebugMessage("onLoadError")
        }
        // TODO deal with this eventually
    }

    override fun onAbort(fileLoadState: FileLoadState) {
        mainScope.launch {
            sendDebugMessage("onAbort")
        }
    }

    override fun onLoadEnd(event: Event, fileLoadState: FileLoadState) {
        mainScope.launch {
            sendDebugMessage("onLoadEnd")
//            sendDebugMessage("filename ${fileLoadState.file.name}")
//            sendDebugMessage("error ${fileLoadState.error}")
//            sendDebugMessage("message ${fileLoadState.errorMessage}")
            //sendDebugMessage("content ${fileLoadState.content}")
//            event as ProgressEvent
//            sendDebugMessage("${event.type} - ${event.loaded} / ${event.total}")
        }
    }
}