package utils

import model.CliContext
import model.PackageInfo

val DEFAULT_CONTEXT = CliContext()



val IPS_IG = PackageInfo(
    "hl7.fhir.uv.ips",
    "1.1.0",
    "4.0.1",
    "http://hl7.org/fhir/uv/ips/STU1.1"
)

val IPS_AU_IG = PackageInfo("hl7.fhir.au.ips",
"current",
"4.0.1",
"http://hl7.org.au/fhir/ips/ImplementationGuide/hl7.fhir.au.ips"
)

val IPS_CURRENT_CONTEXT =  CliContext()
    .setSv("4.0.1")
    .addIg(PackageInfo.igLookupString(IPS_IG))
    .setExtensions(listOf("any"))

val IPS_AU_CONTEXT = CliContext()
    .setSv("4.0.1")
    .addIg(PackageInfo.igLookupString(IPS_AU_IG))
    .setExtensions(listOf("any"))

enum class Preset(val key: String, val label: String, val cliContext: CliContext?, val packageInfo: Set<PackageInfo>) {
    DEFAULT("DEFAULT", "FHIR 4.0 Resource", DEFAULT_CONTEXT, setOf()),
    IPS_CURRENT("IPS_CURRENT", "IPS Document", IPS_CURRENT_CONTEXT, setOf(IPS_IG)),
    IPS_AU("IPS_AU", "Australian IPS Document", IPS_AU_CONTEXT,setOf(IPS_AU_IG));

    companion object {
        fun getSelectedCliContext(key: String?) : CliContext? {
            for (preset in Preset.values()) {
                if (key == preset.key) {
                    return preset.cliContext
                }
            }
            return null
        }

        fun getSelectedLabel(key: String?) : String? {
            for (preset in Preset.values()) {
                if (key == preset.key) {
                    return preset.label
                }
            }
            return null
        }

        fun getSelectedIgPackageInfo(key: String?) : Set<PackageInfo>? {
            for (preset in Preset.values()) {
                if (key == preset.key) {
                    return preset.packageInfo
                }
            }
            return null
        }
    }
}

