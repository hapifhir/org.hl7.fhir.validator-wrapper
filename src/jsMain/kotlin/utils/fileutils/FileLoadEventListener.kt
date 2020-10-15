package utils.fileutils

import org.w3c.dom.events.Event

interface FileLoadEventListener {
    fun onLoadStart(fileLoadState: FileLoadState)
    fun onLoadProgress(fileLoadState: FileLoadState)
    fun onLoadComplete(event: Event, fileLoadState: FileLoadState)
    fun onLoadError(fileLoadState: FileLoadState)
    fun onAbort(fileLoadState: FileLoadState)
    fun onLoadEnd(event: Event, fileLoadState: FileLoadState)
}