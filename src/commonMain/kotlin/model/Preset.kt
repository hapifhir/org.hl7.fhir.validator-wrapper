package model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Preset (
    val key: String,
    val localizedLabels: Map<String, String>,
    @Contextual val cliContext: CliContext,
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

        fun getLocalizedCliContextFromPresets(cliContext: CliContext, presets: List<Preset>): CliContext? {
            if (presets.isEmpty() || cliContext.getBaseEngine() == null) {
                return cliContext
            }
            val presetCliContext = getSelectedPreset(cliContext.getBaseEngine(), presets)?.cliContext
            if (presetCliContext != null) {
                return CliContext()
                    .setLocale(cliContext.getLanguageCode())
                    .setBaseEngine(cliContext.getBaseEngine())
            }
            return presetCliContext
        }
    }
}