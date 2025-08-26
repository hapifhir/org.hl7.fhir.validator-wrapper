package reactredux.slices

import model.BundleValidationRule
import model.ValidationContext
import model.PackageInfo
import model.ValidationEngineSettings
import redux.RAction

object ValidationContextSlice {

    data class State(
        val igPackageInfoSet: Set<PackageInfo> = mutableSetOf(),
        val extensionSet: Set<String> = mutableSetOf(),
        val profileSet: Set<String> = mutableSetOf(),
        val bundleValidationRuleSet: Set<BundleValidationRule> = mutableSetOf(),
        val validationEngineSettings: ValidationEngineSettings = ValidationEngineSettings().setBaseEngine("DEFAULT"),
        val validationContext: ValidationContext = ValidationContext().setBaseEngine("DEFAULT")
    )

    data class UpdateIgPackageInfoSet(val packageInfo: Set<PackageInfo>, val resetBaseEngine: Boolean = true) : RAction

    data class UpdateValidationEngineSettings(val validationEngineSettings: ValidationEngineSettings, val resetBaseEngine : Boolean = true) : RAction

    data class UpdateValidationContext(val validationContext: ValidationContext, val resetBaseEngine : Boolean = true) : RAction

    data class UpdateExtensionSet(val extensionSet: Set<String>, val resetBaseEngine : Boolean = true) : RAction

    data class UpdateProfileSet(val profileSet: Set<String>, val resetBaseEngine : Boolean = true) : RAction

    data class UpdateBundleValidationRuleSet(val bundleValidationRuleSet: Set<BundleValidationRule>, val resetBaseEngine : Boolean = true) : RAction

    private fun resetBaseEngine(validationContext: ValidationContext, resetBaseEngine: Boolean): ValidationContext {
        return if (resetBaseEngine) validationContext.setBaseEngine(null) else validationContext //FIXME apply to validationEngineSettings
    }

    fun reducer(state: State = State(), action: RAction): State {
        return when (action) {
            is UpdateIgPackageInfoSet -> state.copy(
                igPackageInfoSet = action.packageInfo,
                validationContext = resetBaseEngine(
                    state.validationContext.setIgs(action.packageInfo.map{PackageInfo.igLookupString(it)}.toList()),
                    action.resetBaseEngine)

            )
            is UpdateValidationEngineSettings -> state.copy(
                validationEngineSettings = action.validationEngineSettings,
                //FIXME reset base engine? call?
            )
            is UpdateValidationContext -> state.copy(
                validationContext = resetBaseEngine(action.validationContext, action.resetBaseEngine)
            )
            is UpdateExtensionSet -> state.copy(
                extensionSet = action.extensionSet,
                validationContext =  resetBaseEngine(state.validationContext
                    .setExtensions(action.extensionSet.toList())
                    , action.resetBaseEngine)
            )
            is UpdateProfileSet -> state.copy(
                profileSet = action.profileSet,
                validationContext =  resetBaseEngine(state.validationContext
                    .setProfiles(action.profileSet.toList())
                    , action.resetBaseEngine)
            )
            is UpdateBundleValidationRuleSet -> state.copy(
                bundleValidationRuleSet = action.bundleValidationRuleSet,
                validationContext =  resetBaseEngine(state.validationContext
                    .setBundleValidationRules(action.bundleValidationRuleSet.toList())
                    , action.resetBaseEngine)
            )
            else -> state
        }
    }
}