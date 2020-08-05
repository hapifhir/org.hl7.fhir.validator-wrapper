package utils

import constants.FhirFormat
import model.CliContext
import model.FileInfo
import model.ValidationRequest

fun assembleRequest(cliContext: CliContext, fileName:String, fileContent: String, fileType: FhirFormat): ValidationRequest {
    return assembleRequest(cliContext, FileInfo(fileName, fileContent, fileType.code))
}

fun assembleRequest(cliContext: CliContext, file: FileInfo): ValidationRequest {
    return assembleRequest(cliContext, listOf(file))
}

fun assembleRequest(cliContext: CliContext, files: List<FileInfo>): ValidationRequest {
    return ValidationRequest(cliContext, files)
}