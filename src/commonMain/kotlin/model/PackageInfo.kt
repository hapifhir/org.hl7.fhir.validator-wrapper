package model

import kotlinx.serialization.Serializable

@Serializable
data class PackageInfo(
    var id: String?,
    var version: String?,
    var fhirVersion: String?,
    var url: String?,
)
