package model

import kotlinx.serialization.Serializable

import kotlin.math.max


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
        } ?: return true
    }

    fun extractMajorMinor(fhirVersion: String): String {
        when (fhirVersion) {
            "DSTU2" -> return "1.0"
            "STU3" -> return "3.0"
            "R4" -> return "4.0"
            "R5" -> return "5.0"
        }
        val versions = fhirVersion.split('.')
        return if (versions.size >= 2) {
            "${versions[0]}.${versions[1]}"
        } else {
            fhirVersion
        }
    }

    class VersionComparator : Comparator<PackageInfo> {
        override fun compare(a: PackageInfo, b: PackageInfo): Int {
            if (a.version.equals("current")) {
                return -1;
            } else if (b.version.equals("current")) {
                return 1;
            }
            if (a.version == null && b.version == null) {
                return 0;
            }
            if (a.version == null && b.version != null) {
                return -1;
            }
            if (b.version == null && a.version != null) {
                return 1;
            }
            return try {
                compare(a.version!!, b.version!!)
            } catch (e : Exception) {
                return a.version!!.compareTo(b.version!!)
            }

        }

        fun compare(a : String, b: String) : Int{
            val aParts: List<String> = a.split(".", "-")
            val bParts: List<String> = b.split(".", "-")
            val length = max(aParts.size.toDouble(), bParts.size.toDouble()).toInt()
            for (i in 0 until length) {
                val aPart = if (i < aParts.size) aParts[i].toInt() else 0
                val bPart = if (i < bParts.size) bParts[i].toInt() else 0
                if (aPart < bPart) return -1
                if (aPart > bPart) return 1
            }
            return 0
        }
    }

    companion object {
        fun igLookupString( packageName : String, packageVersion: String) : String {
            return "${packageName}#${packageVersion}"
        }

        fun igLookupString(packageInfo: PackageInfo) : String {
            return igLookupString(packageInfo.id!!, packageInfo.version!!)
        }
    }
}
