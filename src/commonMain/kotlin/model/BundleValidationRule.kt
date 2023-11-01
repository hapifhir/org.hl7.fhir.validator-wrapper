package model

expect class BundleValidationRule() {
    fun getRule(): String
    fun setRule(rule: String): BundleValidationRule

    fun getProfile(): String
    fun setProfile(profile: String): BundleValidationRule
}