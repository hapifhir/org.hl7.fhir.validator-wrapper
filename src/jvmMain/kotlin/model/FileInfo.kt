package model

import constants.FhirFormat

actual typealias FileInfo = org.hl7.fhir.validation.service.model.FileInfo

fun FileInfo.asString(): String {
    return "\nFileName -> ${this.fileName}" +
            "\nFileType -> ${this.fileType}" +
            "\nFileContent -> ${this.fileContent}"
}

fun FileInfo.isValid(): Boolean {
    return isValidFileName() && isValidFileType() && isValidFileContent()
}

fun FileInfo.isValidFileName(): Boolean {
    return (this.fileName != null && this.fileName.isNotEmpty())
}

fun FileInfo.isValidFileType(): Boolean {
    return (this.fileType == null || FhirFormat.fromCode(this.fileType) != null)
}

fun FileInfo.isValidFileContent(): Boolean {
    return (this.fileContent != null)
}