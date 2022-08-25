package controller.ig

import org.hl7.fhir.utilities.npm.PackageClient
import org.koin.core.KoinComponent
import org.koin.core.inject
import model.PackageInfo

class IgControllerImpl : IgController, KoinComponent {

    private val packageClient by inject<PackageClient>()

    private val SIMPLIFIER_VERSIONS = listOf(
        "DSTU2",
        "STU3",
        "R4"
    )

    override suspend fun listIgsFromRegistry(): MutableList<PackageInfo> {
        // TODO blocking call fix
        val packageList = packageClient.listFromRegistry(null, null, null)

        val packageInfoList = packageList?.map {
            PackageInfo(id = it.id, version = it.version, fhirVersion = it.fhirVersion, url = it.url)
        }?.toMutableList() ?: mutableListOf()

      return packageInfoList
    }

    override suspend fun listIgsFromSimplifier(): MutableList<PackageInfo> {
        return listIgsFromSimplifier(null);
    }
    override suspend fun listIgsFromSimplifier(igPackageName : String?): MutableList<PackageInfo> {
        val packageInfoList: MutableList<PackageInfo> = mutableListOf()

        SIMPLIFIER_VERSIONS.forEach({
            val packageList = packageClient.search(igPackageName, null, it, false)
            packageList?.map {
                PackageInfo(id = it.id, version = null, fhirVersion = null, url = null)
            }?.toMutableList()?.forEach({
               packageInfoList += it
            })
        })
        return packageInfoList
    }

    override suspend fun listIgVersionsFromSimplifier(igPackageName : String?) : MutableList<PackageInfo> {
        val packageList = packageClient.getVersions(igPackageName)
        val packageInfoList = packageList?.map {
            PackageInfo(id = it.id, version = it.version, fhirVersion = it.fhirVersion, url = it.url)
        }?.toMutableList() ?: mutableListOf()
        return packageInfoList
    }
}