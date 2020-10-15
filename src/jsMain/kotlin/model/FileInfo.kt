package model

import kotlinx.serialization.Serializable

@Serializable
actual class FileInfo actual constructor() {

    var fileName: String = ""
    var fileContent: String = ""
    var fileType: String = "json"

    constructor(fileName: String, fileContent: String, fileType: String) : this() {
        this.fileName = fileName
        this.fileContent = fileContent
        this.fileType = fileType
    }

    actual fun getFileName(): String {
        return fileName
    }

    actual fun setFileName(fileName: String): FileInfo {
        this.fileName = fileName
        return this
    }

    actual fun getFileContent(): String {
        return fileContent
    }

    actual fun setFileContent(fileContent: String): FileInfo {
        this.fileContent = fileContent
        return this
    }

    actual fun getFileType(): String {
        return fileType
    }

    actual fun setFileType(fileType: String): FileInfo {
        this.fileType = fileType
        return this
    }

    fun prettyPrint(): String {
        return "fileName:: ${fileName} \nfileType:: ${fileType}\nfileContent:: ${fileContent}"
    }
}