package utils

import constants.FhirFormat
import model.ValidationContext
import model.FileInfo
import model.ValidationRequest

fun assembleRequest(
    validationContext: ValidationContext,
    fileName: String,
    fileContent: String,
    fileType: FhirFormat?,
): ValidationRequest {
    return assembleRequest(validationContext, FileInfo(fileName, fileContent, if (fileType == null) null else fileType.code))
}

fun assembleRequest(validationContext: ValidationContext, file: FileInfo): ValidationRequest {
    return assembleRequest(validationContext, listOf(file))
}

fun assembleRequest(validationContext: ValidationContext, files: List<FileInfo>): ValidationRequest {
    return ValidationRequest(validationContext, files)
}
