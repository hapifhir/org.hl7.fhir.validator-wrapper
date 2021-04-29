package model

import kotlinx.serialization.Serializable

@Serializable
data class PackageInfo(
    var id: String? = null,
    var version: String? = null,
    var fhirVersion: String? = null,
    var url: String? = null,
) {
    override fun equals(other: Any?): Boolean{
        if (this === other) return true
        if (!(other is PackageInfo)) return false
        other as PackageInfo
        return ((other.id == id) && (other.version == version) && (other.fhirVersion == fhirVersion) && (other.url == url))
    }
}
