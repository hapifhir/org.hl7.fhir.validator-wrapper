package reactredux.slices

import model.Preset
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
        val cliContext: CliContext = CliContext().setBaseEngine("DEFAULT")
    )

    data class UpdateIgPackageInfoSet(val packageInfo: Set<PackageInfo>, val resetBaseEngine: Boolean = true) : RAction

    data class UpdateCliContext(val cliContext: CliContext, val resetBaseEngine : Boolean = true) : RAction

    data class UpdateExtensionSet(val extensionSet: Set<String>, val resetBaseEngine : Boolean = true) : RAction

    data class UpdateProfileSet(val profileSet: Set<String>, val resetBaseEngine : Boolean = true) : RAction

    data class UpdateBundleValidationRuleSet(val bundleValidationRuleSet: Set<BundleValidationRule>, val resetBaseEngine : Boolean = true) : RAction

    private fun resetBaseEngine(cliContext: CliContext, resetBaseEngine: Boolean): CliContext {
        return if (resetBaseEngine) cliContext.setBaseEngine(null) else cliContext
    }

    fun reducer(state: State = State(), action: RAction): State {
        return when (action) {
            is UpdateIgPackageInfoSet -> state.copy(
                igPackageInfoSet = action.packageInfo,
                cliContext = resetBaseEngine(
                    state.cliContext.setIgs(action.packageInfo.map{PackageInfo.igLookupString(it)}.toList()),
                    action.resetBaseEngine)

            )
            is UpdateCliContext -> state.copy(
                cliContext = resetBaseEngine(action.cliContext, action.resetBaseEngine)
            )
            is UpdateExtensionSet -> state.copy(
                extensionSet = action.extensionSet,
                cliContext =  resetBaseEngine(state.cliContext
                    .setExtensions(action.extensionSet.toList())
                    , action.resetBaseEngine)
            )
            is UpdateProfileSet -> state.copy(
                profileSet = action.profileSet,
                cliContext =  resetBaseEngine(state.cliContext
                    .setProfiles(action.profileSet.toList())
                    , action.resetBaseEngine)
            )
            is UpdateBundleValidationRuleSet -> state.copy(
                bundleValidationRuleSet = action.bundleValidationRuleSet,
                cliContext =  resetBaseEngine(state.cliContext
                    .setBundleValidationRules(action.bundleValidationRuleSet.toList())
                    , action.resetBaseEngine)
            )
            else -> state
        }
    }
}