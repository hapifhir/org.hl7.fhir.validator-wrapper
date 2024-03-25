package controller.conversion

import controller.validation.ValidationServiceFactory
import model.CliContext
import org.hl7.fhir.utilities.TimeTracker
import org.hl7.fhir.utilities.VersionUtilities
import org.hl7.fhir.validation.ValidationEngine
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.deleteIfExists

class ConversionControllerImpl : ConversionController, KoinComponent {

    private val validationServiceFactory by inject<ValidationServiceFactory>()

    override suspend fun convertRequest(content: String, type: String?, version: String?, toType: String?,
                                        toVersion: String?, sessionId: String?): ConversionResponse {
        var fromType = type ?: "json"
        var fromVersion = version ?: "5.0"
        var session = sessionId ?: "new"

        val cliContext = CliContext()

        var validator: ValidationEngine? = validationServiceFactory.getCachedValidator(session)
        if (validator == null || validator.version != fromVersion) {
            val definitions = VersionUtilities.packageForVersion(fromVersion) + "#" +
                    VersionUtilities.getCurrentVersion(fromVersion)
            val timeTracker = TimeTracker()
            session = validationServiceFactory.getValidationService()
                .initializeValidator(cliContext, definitions, timeTracker, "new")
            validator = validationServiceFactory.getCachedValidator(session)
            validator?.version = fromVersion
        }

        var input: Path? = null
        var output: Path? = null
        try {
            input = Files.createTempFile("input", ".$fromType")
            Files.writeString(input.toAbsolutePath(), content)
            cliContext.addSource(input.toAbsolutePath().toString())

            output = Files.createTempFile("convert", ".${toType ?: fromType}")

            cliContext.targetVer = toVersion ?: fromVersion

            cliContext.output = output.toAbsolutePath().toString()
            validationServiceFactory.getValidationService().convertSources(cliContext, validator)
            return ConversionResponse(Files.readString(output.toAbsolutePath()), session)
        } finally {
            input?.deleteIfExists()
            output?.deleteIfExists()
        }
    }
}