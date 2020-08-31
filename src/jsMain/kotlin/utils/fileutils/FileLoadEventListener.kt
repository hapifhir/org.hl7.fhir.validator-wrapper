package utils.fileutils

interface FileLoadEventListener {
    fun onLoadStart(fileLoadState: FileLoadState)
    fun onLoadProgress(fileLoadState: FileLoadState)
    fun onLoadComplete(fileLoadState: FileLoadState)
    fun onLoadError(fileLoadState: FileLoadState)
}