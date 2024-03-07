package controller.conversion

import controller.validation.ValidationServiceFactory
import model.CliContext
import org.hl7.fhir.validation.ValidationEngine
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.deleteIfExists

class ConversionControllerImpl : ConversionController, KoinComponent {

    private val validationServiceFactory by inject<ValidationServiceFactory>()

    override suspend fun convertRequest(content: String, type: String?, version: String?, toType: String?,
                                        toVersion: String?): String {
        val fromType = type ?: "json"
        val fromVersion = version ?: "5.0"

        val cliContext = CliContext()
        cliContext.sv = fromVersion
        cliContext.targetVer = toVersion ?: fromVersion

        var validator: ValidationEngine? = validationServiceFactory.getValidationEngine(cliContext)

        var input: Path? = null
        var output: Path? = null
        try {
            input = Files.createTempFile("input", ".$fromType")
            Files.writeString(input.toAbsolutePath(), content)
            cliContext.addSource(input.toAbsolutePath().toString())

            output = Files.createTempFile("convert", ".${toType ?: fromType}")
            cliContext.output = output.toAbsolutePath().toString()

            validationServiceFactory.getValidationService().convertSources(cliContext, validator)
            return Files.readString(output.toAbsolutePath())
        } finally {
            input?.deleteIfExists()
            output?.deleteIfExists()
        }
    }
}