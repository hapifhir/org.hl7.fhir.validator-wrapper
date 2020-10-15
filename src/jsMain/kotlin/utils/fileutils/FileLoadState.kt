package utils.fileutils

import org.w3c.files.File

data class FileLoadState(
    var file: File,
    var content: String = "",
    var loaded: Double = 0.0,
    var total: Double = 0.0,
    var completed: Boolean = false,
    var error: Boolean = false,
    var errorMessage: String = "",
)

