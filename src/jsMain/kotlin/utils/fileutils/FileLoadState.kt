package utils.fileutils

import constants.MIMEType
import model.FileInfo
import org.w3c.files.File

data class FileLoadState(
    var file: File,
    var content: String = "",
    var loaded: Double = 0.0,
    var total: Double = 0.0,
    var completed: Boolean = false,
    var error: Boolean = false,
    var errorMessage: String = ""
) {
    companion object {
        fun toFileInfo(file: FileLoadState): FileInfo {
            val fileInfo = FileInfo()
            fileInfo.fileName = file.file.name
            fileInfo.fileContent = file.content
            fileInfo.fileType = MIMEType.fromFileType(file.file.type)?.fhirType!!
            return fileInfo
        }
    }
}

