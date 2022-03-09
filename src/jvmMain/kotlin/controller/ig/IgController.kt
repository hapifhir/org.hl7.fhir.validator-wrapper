package controller.ig

import model.PackageInfo

interface IgController {
    suspend fun listIgsFromRegistry(): MutableList<PackageInfo>

    suspend fun listIgsFromSimplifier(): MutableList<PackageInfo>

    suspend fun listIgVersionsFromSimplifier(igPackageName : String?): MutableList<PackageInfo>
}