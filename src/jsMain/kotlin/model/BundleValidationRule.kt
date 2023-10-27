package model

import kotlinx.js.Object
import kotlinx.serialization.Serializable
import utils.Preset

@Serializable
actual class BundleValidationRule actual constructor() {

    private var rule: String = ""
    private var profile: String = ""

    actual fun getRule(): String {
        return rule
    }

    actual fun setRule(rule: String): BundleValidationRule {
        this.rule = rule
        return this
    }

    actual fun getProfile(): String {
        return profile
    }

    actual fun setProfile(profile: String): BundleValidationRule {
        this.profile = profile
        return this
    }

    override fun hashCode(): Int {
        return toDisplayString(this).hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other !is BundleValidationRule) {
            return false
        } else {
            return rule.equals(other.getRule()) && profile.equals(other.getProfile())
        }
    }

    companion object {
        fun toDisplayString(rule:BundleValidationRule): String {
            return "${rule.rule} ${rule.profile}"
        }

        fun findByDisplayString(displayString : String, collection : Collection<BundleValidationRule>) : BundleValidationRule? {
            for (rule in collection) {
                if (displayString == toDisplayString(rule)) {
                    return rule
                }
            }
            return null
        }
    }
}