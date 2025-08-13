package utils

import constants.FhirFormat
import model.ValidationContext
import model.FileInfo
import model.ValidationEngineSettings
import model.ValidationRequest

fun assembleRequest(
    validationEngineSettings: ValidationEngineSettings,
    validationContext: ValidationContext,
    fileName: String,
    fileContent: String,
    fileType: FhirFormat?,
): ValidationRequest {
    return assembleRequest(validationEngineSettings, validationContext, FileInfo(fileName, fileContent, if (fileType == null) null else fileType.code))
}

fun assembleRequest(validationEngineSettings: ValidationEngineSettings, validationContext: ValidationContext, file: FileInfo): ValidationRequest {
    return assembleRequest(validationEngineSettings, validationContext, listOf(file))
}

fun assembleRequest(validationEngineSettings: ValidationEngineSettings, validationContext: ValidationContext, files: List<FileInfo>): ValidationRequest {
    return ValidationRequest(validationEngineSettings, validationContext, files)
}
