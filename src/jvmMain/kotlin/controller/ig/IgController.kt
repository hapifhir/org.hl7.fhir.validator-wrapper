package controller.ig

import model.PackageInfo

interface IgController {
    suspend fun listIgs(): MutableList<PackageInfo>
}