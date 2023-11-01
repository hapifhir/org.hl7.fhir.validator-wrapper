package reactredux.slices

import model.BundleValidationRule
import model.CliContext
import model.PackageInfo
import redux.RAction

object ValidationContextSlice {

    data class State(
        val igPackageInfoSet: Set<PackageInfo> = mutableSetOf(),
        val extensionSet: Set<String> = mutableSetOf(),
        val profileSet: Set<String> = mutableSetOf(),
        val bundleValidationRuleSet: Set<BundleValidationRule> = mutableSetOf(),
        val cliContext: CliContext = CliContext()
    )

    data class UpdateIgPackageInfoSet(val packageInfo: Set<PackageInfo>) : RAction

    data class UpdateCliContext(val cliContext: CliContext) : RAction

    data class UpdateExtensionSet(val extensionSet: Set<String>) : RAction

    data class UpdateProfileSet(val profileSet: Set<String>) : RAction

    data class UpdateBundleValidationRuleSet(val bundleValidationRuleSet: Set<BundleValidationRule>) : RAction

    fun reducer(state: State = State(), action: RAction): State {
        return when (action) {
            is UpdateIgPackageInfoSet -> state.copy(
                igPackageInfoSet = action.packageInfo,
                cliContext = state.cliContext.setIgs(action.packageInfo.map{PackageInfo.igLookupString(it)}.toList())
            )
            is UpdateCliContext -> state.copy(
                cliContext = action.cliContext
            )
            is UpdateExtensionSet -> state.copy(
                extensionSet = action.extensionSet,
                cliContext = state.cliContext.setExtensions(action.extensionSet.toList())
            )
            is UpdateProfileSet -> state.copy(
                profileSet = action.profileSet,
                cliContext = state.cliContext.setProfiles(action.profileSet.toList())
            )
            is UpdateBundleValidationRuleSet -> state.copy(
                bundleValidationRuleSet = action.bundleValidationRuleSet,
                cliContext = state.cliContext.setBundleValidationRules(action.bundleValidationRuleSet.toList())
            )
            else -> state
        }
    }
}