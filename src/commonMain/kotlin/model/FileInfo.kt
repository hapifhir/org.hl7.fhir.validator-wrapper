package model

expect class FileInfo() {
    fun setFileName(fileName: String): FileInfo
    fun getFileName(): String
    fun setFileContent(fileContent: String): FileInfo
    fun getFileContent(): String
    fun setFileType(fileType: String): FileInfo
    fun getFileType(): String
}
