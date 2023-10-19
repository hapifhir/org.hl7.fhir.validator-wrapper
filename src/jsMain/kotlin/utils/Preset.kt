package utils

import model.CliContext

val DEFAULT_CONTEXT = CliContext()

val IPS_CURRENT_CONTEXT =  CliContext()
    .setSv("4.0.1")
    .addIg("hl7.fhir.uv.ips#1.1.0")
enum class Preset(val key: String, val label: String, val cliContext: CliContext?) {
    DEFAULT("DEFAULT", "Default", DEFAULT_CONTEXT),
    IPS_CURRENT("IPS_CURRENT", "IPS (current: 1.1.0)", IPS_CURRENT_CONTEXT),
    IPS_1_0_0("IPS_1_0_0", "IPS (1.0.0)", null),
    IPS_0_3_0("IPS_0_3_0", "IPS (0.3.0)", null),
    IPS_0_2_0("IPS_0_2_0", "IPS (0.2.0)", null),
    IPS_AU("IPS_AU", "IPS (Australia)", null);

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
    }
}

