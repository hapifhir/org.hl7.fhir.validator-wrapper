package utils

import model.*

fun buildCompleteValidationContext(
    baseContext: ValidationContext,
    igPackageInfoSet: Set<PackageInfo>,
    profileSet: Set<String>,
    extensionSet: Set<String>,
    bundleValidationRuleSet: Set<BundleValidationRule>,
    presets: List<Preset>
): ValidationContext {
    // Start with copy of baseContext to preserve all flags and settings
    val result = ValidationContext(baseContext)

    // Convert PackageInfo set to IG lookup strings
    val userIgStrings = igPackageInfoSet.map { PackageInfo.igLookupString(it) }

    // If preset is selected, merge preset config with user selections
    val baseEngine = baseContext.getBaseEngine()
    if (baseEngine != null && presets.isNotEmpty()) {
        val selectedPreset = Preset.getSelectedPreset(baseEngine, presets)
        if (selectedPreset != null) {
            val presetContext = selectedPreset.validationContext

            // Merge approach: Union of preset + user selections (no duplicates)
            val mergedIgs = (presetContext.getIgs() + userIgStrings).toSet().toList()
            val mergedProfiles = (presetContext.getProfiles() + profileSet).toSet().toList()
            val mergedExtensions = (presetContext.getExtensions() + extensionSet).toSet().toList()
            val mergedBundleRules = (presetContext.getBundleValidationRules() + bundleValidationRuleSet).toSet().toList()

            return result
                .setIgs(mergedIgs)
                .setProfiles(mergedProfiles)
                .setExtensions(mergedExtensions)
                .setBundleValidationRules(mergedBundleRules)
        }
    }

    // No preset or preset not found - use only user selections
    return result
        .setIgs(userIgStrings)
        .setProfiles(profileSet.toList())
        .setExtensions(extensionSet.toList())
        .setBundleValidationRules(bundleValidationRuleSet.toList())
}
