package utils

import constants.FhirFormat
import model.CliContext
import model.FileInfo
import model.ValidationRequest

fun assembleRequest(
    cliContext: CliContext,
    fileName: String,
    fileContent: String,
    fileType: FhirFormat?,
): ValidationRequest {
    return assembleRequest(cliContext, FileInfo(fileName, fileContent, if (fileType == null) null else fileType.code))
}

fun assembleRequest(cliContext: CliContext, file: FileInfo): ValidationRequest {
    return assembleRequest(cliContext, listOf(file))
}

fun assembleRequest(cliContext: CliContext, files: List<FileInfo>): ValidationRequest {
    val assembledCliContext : CliContext= assembleCliContext(cliContext);
    return ValidationRequest(assembledCliContext, files)
}

fun assembleCliContext(cliContext: CliContext): CliContext {
    if (cliContext.getBaseEngine() == null) {
        return cliContext
    }
    console.log("Building new CLI Context")
    val baseEngineContext = CliContext()

    baseEngineContext.setBaseEngine(cliContext.getBaseEngine())
    for (profile in cliContext.getProfiles()) {
        baseEngineContext.addProfile(profile)
    }
    baseEngineContext.setLocale(cliContext.getLanguageCode())
    return baseEngineContext;
}
