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
        return ((other.id == id) && (other.version == version) && (other.fhirVersion == fhirVersion) && (other.url == url))
    }

    fun fhirVersionMatches(fhirVersion: String): Boolean {
        this.fhirVersion?.let {
            return extractMajorMinor(this.fhirVersion!!) == extractMajorMinor(fhirVersion)
        } ?: return false
    }

    fun extractMajorMinor(fhirVersion: String): String {
        val versions = fhirVersion.split('.')
        return if (versions.size >= 2) {
            "${versions[0]}.${versions[1]}"
        } else {
            fhirVersion
        }
    }
}
