package controller.ig

import org.hl7.fhir.utilities.npm.PackageClient
import org.koin.core.KoinComponent
import org.koin.core.inject

class IgControllerImpl : IgController, KoinComponent {

    private val packageClient by inject<PackageClient>()

    override suspend fun listIgs(): MutableList<String> {
        val packageList = packageClient.listFromRegistry(null, null, null)
        return packageList?.map {
            it.url
        }?.toMutableList() ?: mutableListOf()
    }
}