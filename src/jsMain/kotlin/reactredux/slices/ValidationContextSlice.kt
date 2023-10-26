package reactredux.slices

import model.CliContext
import model.PackageInfo
import redux.RAction

object ValidationContextSlice {

    data class State(
        val igPackageInfoSet: Set<PackageInfo> = mutableSetOf<PackageInfo>(),
        val extensionSet: Set<String> = mutableSetOf<String>(),
        val profileSet: Set<String> = mutableSetOf<String>(),
        val cliContext: CliContext = CliContext()
    )

    data class UpdateIgPackageInfoSet(val packageInfo: Set<PackageInfo>) : RAction

    data class UpdateCliContext(val cliContext: CliContext) : RAction

    data class UpdateExtensionSet(val extensionSet: Set<String>) : RAction

    data class UpdateProfileSet(val profileSet: Set<String>) : RAction

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
            else -> state
        }
    }
}