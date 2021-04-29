package model

import kotlinx.serialization.Serializable

@Serializable
data class IGResponse(var packageInfo: MutableList<PackageInfo> = mutableListOf())