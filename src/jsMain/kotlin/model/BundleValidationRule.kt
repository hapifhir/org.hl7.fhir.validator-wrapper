package model

import kotlinx.serialization.Serializable

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

}