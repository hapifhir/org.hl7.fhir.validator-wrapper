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
        return packageList?.map {
            PackageInfo(id = it.id, version = it.version, fhirVersion = it.fhirVersion, url = it.url)
        }?.toMutableList() ?: mutableListOf()
    }

    override suspend fun listIgsFromSimplifier(): MutableList<PackageInfo> {
        val packageInfoList: MutableList<PackageInfo> = mutableListOf()

        SIMPLIFIER_VERSIONS.forEach({
            val packageList = packageClient.search(null, null, it, false)
            packageList?.map {
                PackageInfo(id = it.id, version = it.version, fhirVersion = it.fhirVersion, url = it.url)
            }?.toMutableList()?.forEach({
                //println("Package: ${it.id} FhirVersion: ${it.fhirVersion}")
                val packageList3 = packageClient.getVersions(it.id)
                packageList3?.map {
                    PackageInfo(id = it.id, version = it.version, fhirVersion = it.fhirVersion, url = it.url)
                }?.toMutableList()?.forEach({
                    if (it.url != null) {    //
                        // println("  Version:${it.version} Url: ${it.url}")
                        val semVersion = "4.0.1"//FhirVersion.fromCode(it.fhirVersion)
                        //println("${it.id} version code:${it.fhirVersion} version semVer:${semVersion}")
                        packageInfoList += PackageInfo(it.id, it.version, semVersion, it.url)
                    }
                })
            })
        })
        return packageInfoList
    }
}