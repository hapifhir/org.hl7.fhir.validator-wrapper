package utils

import constants.MIMEType
import model.FileInfo
import org.w3c.dom.ErrorEvent
import org.w3c.files.File
import org.w3c.files.FileReader
import org.w3c.xhr.ProgressEvent

const val EVENT_LOAD = "load"
const val EVENT_LOAD_START = "loadstart"
const val EVENT_ERROR = "error"
const val EVENT_PROGRESS = "progress"

fun parseFile(file: File, listener: FileEventListener) {
    val reader = FileReader()
    reader.addEventListener(type = EVENT_LOAD_START, callback = {
        listener.onLoadStart(file = file)
    })
    reader.addEventListener(type = EVENT_LOAD, callback = {
        listener.onLoadComplete(file = file, result = reader.result as String)
    })
    reader.addEventListener(type = EVENT_PROGRESS, callback = {
        it as ProgressEvent
        listener.onLoadProgress(file = file, loaded = it.loaded as Double, total = it.total as Double)
    })
    reader.addEventListener(type = EVENT_ERROR, callback = {
        it as ErrorEvent
        listener.onLoadError(file = file, error = it.error, message = it.message)
    })
    reader.readAsText(file)
}

fun convertToFileInfo(file: FileLoadState): FileInfo {
    val fileInfo = FileInfo()
    fileInfo.fileName = file.file.name
    fileInfo.fileContent = file.content
    fileInfo.fileType = MIMEType.get(file.file.type)?.fhirType as String
    return fileInfo
}

data class FileLoadState(
    var file: File,
    var content: String = "",
    var loaded: Double = 0.0,
    var total: Double = 0.0,
    var completed: Boolean = false,
    var error: Boolean = false,
    var errorMessage: String = ""
)

interface FileEventListener {
    fun onLoadStart(file: File)
    fun onLoadProgress(file: File, loaded: Double, total: Double)
    fun onLoadComplete(file: File, result: String)
    fun onLoadError(file: File, error: Any?, message: String)
}

