package utils

import model.CliContext
import model.PackageInfo

import constants.ANY_EXTENSION
import model.BundleValidationRule

val DEFAULT_CONTEXT = CliContext()

val IPS_IG = PackageInfo(
    "hl7.fhir.uv.ips",
    "1.1.0",
    "4.0.1",
    "http://hl7.org/fhir/uv/ips/STU1.1"
)

val IPS_AU_IG = PackageInfo(
    "hl7.fhir.au.ips",
    "current",
    "4.0.1",
    "http://hl7.org.au/fhir/ips/ImplementationGuide/hl7.fhir.au.ips"
)

val CDA_IG = PackageInfo(
    "hl7.cda.uv.core",
    "2.0.0-sd-ballot",
    "5.0.0",
    "http://hl7.org/cda/stds/core/ImplementationGuide/hl7.cda.uv.core"
)

val CCDA_IG = PackageInfo(
    "hl7.cda.us.ccda",
    "current",
    "5.0.0",
    "http://hl7.org/fhir/us/ccda/ImplementationGuide/hl7.fhir.us.ccda"
)

val SQL_ON_FHIR_IG = PackageInfo(
    "hl7.fhir.uv.sql-on-fhir",
    "current",
    "5.0.0",
    "http://hl7.org/fhir/uv/sql-on-fhir/ImplementationGuide/hl7.fhir.uv.sql-on-fhir"
)

val IPS_BUNDLE_PROFILE = "http://hl7.org/fhir/uv/ips/StructureDefinition/Bundle-uv-ips"

val IPS_AU_BUNDLE_PROFILE = "http://hl7.org.au/fhir/ips/StructureDefinition/Bundle-au-ips"

val IPS_CONTEXT = CliContext()
    .setSv("4.0.1")
    .addIg(PackageInfo.igLookupString(IPS_IG))
    .setExtensions(listOf(ANY_EXTENSION))
    .setCheckIPSCodes(true)
    .setBundleValidationRules(listOf(
        BundleValidationRule()
            .setRule("Composition:0")
            .setProfile("http://hl7.org/fhir/uv/ips/StructureDefinition/Composition-uv-ips")
    ))

val IPS_AU_CONTEXT = CliContext()
    .setSv("4.0.1")
    .addIg(PackageInfo.igLookupString(IPS_AU_IG))
    .setExtensions(listOf(ANY_EXTENSION))
    .setCheckIPSCodes(true)
    .setBundleValidationRules(listOf(
        BundleValidationRule()
            .setRule("Composition:0")
            .setProfile("http://hl7.org.au/fhir/ips/StructureDefinition/Composition-au-ips")
    ))

val CDA_CONTEXT = CliContext()
    .setSv("5.0.0")
    .addIg(PackageInfo.igLookupString(CDA_IG))

val CCDA_CONTEXT = CliContext()
    .setSv("5.0.0")
    .addIg(PackageInfo.igLookupString(CCDA_IG))

val SQL_VIEW_CONTEXT = CliContext()
    .setSv("5.0.0")
    .addIg(PackageInfo.igLookupString(SQL_ON_FHIR_IG))

enum class Preset(
    val key: String,
    val polyglotKey: String,
    val cliContext: CliContext,
    val igPackageInfo: Set<PackageInfo>,
    val extensionSet: Set<String>,
    val profileSet: Set<String>
) {
    DEFAULT(
        "DEFAULT",
        "preset_fhir_resource",
        DEFAULT_CONTEXT,
        setOf(),
        setOf(),
        setOf()
    ),
    IPS_CURRENT(
        "IPS",
        "preset_ips",
        IPS_CONTEXT,
        setOf(IPS_IG),
        setOf(ANY_EXTENSION),
        setOf(IPS_BUNDLE_PROFILE)
    ),
    IPS_AU(
        "IPS_AU",
        "preset_ips_au",
        IPS_AU_CONTEXT,
        setOf(IPS_AU_IG),
        setOf(ANY_EXTENSION),
        setOf(IPS_AU_BUNDLE_PROFILE)
    ),
    CDA(
        "CDA",
        "present_cda",
        CDA_CONTEXT,
        setOf(CDA_IG),
        setOf(),
        setOf()
    ),
    US_CCDA(
        "US_CDA",
        "preset_us_ccda",
        CCDA_CONTEXT,
        setOf(CCDA_IG),
        setOf(),
        setOf()
    ),
    SQL_VIEW(
        "SQL_VIEW",
        "preset_sql_view",
        SQL_VIEW_CONTEXT,
        setOf(SQL_ON_FHIR_IG),
        setOf(),
        setOf()
    )
    ;

    companion object {
        fun getSelectedPreset(key: String?): Preset? {
            for (preset in Preset.values()) {
                if (key == preset.key) {
                    return preset
                }
            }
            return null
        }
    }
}

