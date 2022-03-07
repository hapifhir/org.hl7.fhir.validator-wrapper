package controller.ig

import model.PackageInfo

interface IgController {
    suspend fun listIgsFromRegistry(): MutableList<PackageInfo>

    suspend fun listIgsFromSimplifier(): MutableList<PackageInfo>
}