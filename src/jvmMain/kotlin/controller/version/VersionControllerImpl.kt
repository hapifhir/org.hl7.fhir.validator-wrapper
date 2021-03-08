package controller.version

import model.ValidationResponse
import org.hl7.fhir.utilities.VersionUtilities
import org.hl7.fhir.validation.cli.model.ValidationRequest
import org.hl7.fhir.validation.cli.services.ValidationService
import org.koin.core.KoinComponent
import org.koin.core.inject

class VersionControllerImpl : VersionController, KoinComponent {

    override suspend fun listSupportedVersions(): MutableList<String> {
        return VersionUtilities.listSupportedVersions()
            ?.split(',')
            ?.map { it.replace("\\s".toRegex(), "") }
            ?.toMutableList() ?: mutableListOf()
    }
}