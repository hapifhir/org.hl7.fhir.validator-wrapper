package model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Preset (
    val key: String,
    val polyglotKey: String,
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
    }
}