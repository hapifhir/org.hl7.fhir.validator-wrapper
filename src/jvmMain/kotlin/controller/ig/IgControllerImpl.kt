package controller.ig

import org.hl7.fhir.utilities.npm.PackageClient
import org.koin.core.KoinComponent
import org.koin.core.inject
import model.PackageInfo

class IgControllerImpl : IgController, KoinComponent {

    private val packageClient by inject<PackageClient>()

    override suspend fun listIgs(): MutableList<PackageInfo> {
        // TODO blocking call fix
        val packageList = packageClient.listFromRegistry(null, null, null)
        return packageList?.map {
            PackageInfo(id = it.id, version = it.version, fhirVersion = it.fhirVersion, url = it.url)
        }?.toMutableList() ?: mutableListOf()
    }
}