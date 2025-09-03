package model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Preset (
    val key: String,
    val localizedLabels: Map<String, String>,
    @Contextual val validationEngineSettings: ValidationEngineSettings,
    @Contextual val validationContext: ValidationContext,
    val igPackageInfo: Set<PackageInfo>,
    val extensionSet: Set<String>,
    val profileSet: Set<String>
) {
    companion object {
        fun getSelectedPreset(key: String?, presets: List<Preset>): Preset? {
            for (preset in presets) {
                if (key == preset.key) {
                    return preset
                }
            }
            return null
        }

        fun getLocalizedValidationContextFromPresets(validationContext: ValidationContext, presets: List<Preset>): ValidationContext? {
            if (presets.isEmpty() || validationContext.getBaseEngine() == null) {
                return validationContext
            }
            val presetValidationContext = getSelectedPreset(validationContext.getBaseEngine(), presets)?.validationContext
            if (presetValidationContext != null) {
                return ValidationContext()
                    .setLocale(validationContext.getLanguageCode())
                    .setBaseEngine(validationContext.getBaseEngine())
            }
            return presetValidationContext
        }

        fun getLocalizedValidationEngineSettingsFromPresets(validationEngineSettings: ValidationEngineSettings, presets: List<Preset>): ValidationEngineSettings? {
            if (presets.isEmpty() || validationEngineSettings.getBaseEngine() == null) {
                return validationEngineSettings
            }
            val presetValidationEngineSettings = getSelectedPreset(validationEngineSettings.getBaseEngine(), presets)?.validationEngineSettings
            if (presetValidationEngineSettings != null) {
                return ValidationEngineSettings()
                    //FIXME set locale
                    //.setLocale(validationContext.getLanguageCode())
                    .setBaseEngine(validationEngineSettings.getBaseEngine())
            }
            return presetValidationEngineSettings
        }
    }
}