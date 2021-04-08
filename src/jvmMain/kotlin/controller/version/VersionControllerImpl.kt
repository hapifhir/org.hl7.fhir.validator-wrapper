package controller.version

import org.hl7.fhir.utilities.VersionUtilities
import org.koin.core.KoinComponent

class VersionControllerImpl : VersionController, KoinComponent {

    override suspend fun listSupportedVersions(): MutableList<String> {
        return VersionUtilities.listSupportedVersions()
            ?.split(',')
            ?.map { it.replace("\\s".toRegex(), "") }
            ?.toMutableList() ?: mutableListOf()
    }
}