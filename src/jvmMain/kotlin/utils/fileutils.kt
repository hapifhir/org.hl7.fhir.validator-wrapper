package utils

import model.FileInfo
import model.asString
import model.isValid
import org.slf4j.Logger

const val INVALID_FILE = "Invalid file entry passed in -> %s"

fun badFileEntryExists(logger: Logger, filesToValidate: MutableCollection<FileInfo>, silent:Boolean = false): Boolean {
    return filesToValidate.filterNot { it.isValid() }
        .onEach { if (!silent) logger.error(INVALID_FILE.format(it.asString())) }
        .isNotEmpty()
}