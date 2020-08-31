package utils.fileutils

import org.w3c.dom.ErrorEvent
import org.w3c.files.File
import org.w3c.files.FileReader
import org.w3c.xhr.ProgressEvent

const val EVENT_LOAD = "load"
const val EVENT_LOAD_START = "loadstart"
const val EVENT_ERROR = "error"
const val EVENT_PROGRESS = "progress"

fun loadFile(file: File, listener: FileLoadEventListener) {
    val reader = FileReader()
    val fileLoadState = FileLoadState(file = file)
    reader.addEventListener(type = EVENT_LOAD_START, callback = {
        listener.onLoadStart(fileLoadState = fileLoadState)
    })
    reader.addEventListener(type = EVENT_LOAD, callback = {
        fileLoadState.completed = true
        fileLoadState.content = reader.result as String
        listener.onLoadComplete(fileLoadState = fileLoadState)
    })
    reader.addEventListener(type = EVENT_PROGRESS, callback = {
        it as ProgressEvent
        fileLoadState.loaded = it.loaded as Double
        fileLoadState.total = it.total as Double
        listener.onLoadProgress(fileLoadState = fileLoadState)
    })
    reader.addEventListener(type = EVENT_ERROR, callback = {
        it as ErrorEvent
        fileLoadState.error = true
        fileLoadState.errorMessage = it.message
        fileLoadState.completed = true
        listener.onLoadError(fileLoadState = fileLoadState)
    })
    reader.readAsText(file)
}