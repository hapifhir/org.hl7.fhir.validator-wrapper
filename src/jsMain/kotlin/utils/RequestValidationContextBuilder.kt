package utils

import model.*

fun buildValidationContextForRequest(
    baseContext: ValidationContext,
    igPackageInfoSet: Set<PackageInfo>,
    profileSet: Set<String>,
    extensionSet: Set<String>,
    bundleValidationRuleSet: Set<BundleValidationRule>,
    presets: List<Preset>
): ValidationContext {


    // If preset is selected, use its ValidationContext
    val baseEngine = baseContext.getBaseEngine()
    if (baseEngine != null && presets.isNotEmpty()) {
        val selectedPreset = Preset.getSelectedPreset(baseEngine, presets)
        if (selectedPreset != null) {
            // Changing a locale doesn't require an entirely new validation engine, so we can send the user selected
            // value as part of the request.
            return ValidationContext(selectedPreset.validationContext).setLocale(baseContext.getLanguageCode())
        }
    }

    // No preset or preset not found - use only user selections
    // Start with copy of baseContext to preserve all flags and settings
    val result = ValidationContext(baseContext)

    // Convert PackageInfo set to IG lookup strings
    val userIgStrings = igPackageInfoSet.map { PackageInfo.igLookupString(it) }

    return result
        .setIgs(userIgStrings)
        .setProfiles(profileSet.toList())
        .setExtensions(extensionSet.toList())
        .setBundleValidationRules(bundleValidationRuleSet.toList())
}
