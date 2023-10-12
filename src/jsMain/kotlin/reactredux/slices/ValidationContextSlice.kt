package reactredux.slices

import model.CliContext
import model.PackageInfo
import redux.RAction

object ValidationContextSlice {

    data class State(
        val selectedIgPackageInfo: Set<PackageInfo> = mutableSetOf<PackageInfo>(),
        val addedExtensionInfo: Set<String> = mutableSetOf<String>(),
        val addedProfiles: Set<String> = mutableSetOf<String>(),
        val cliContext: CliContext = CliContext()
    )

    data class UpdateSelectedIgPackageInfo(val packageInfo: Set<PackageInfo>) : RAction

    data class UpdateCliContext(val cliContext: CliContext) : RAction

    data class UpdateAddedExtensionUrl(val extensionUrls: Set<String>) : RAction

    data class UpdateAddedProfile(val profiles: Set<String>) : RAction

    fun reducer(state: State = State(), action: RAction): State {
        return when (action) {
            is UpdateSelectedIgPackageInfo -> state.copy(
                selectedIgPackageInfo = action.packageInfo,
                cliContext = state.cliContext.setIgs(action.packageInfo.map{PackageInfo.igLookupString(it)}.toList())
            )
            is UpdateCliContext -> state.copy(
                cliContext = action.cliContext
            )
            is UpdateAddedExtensionUrl -> state.copy(
                addedExtensionInfo = action.extensionUrls,
                cliContext = state.cliContext.setExtensions(action.extensionUrls.toList())
            )
            is UpdateAddedProfile -> state.copy(
                addedProfiles = action.profiles,
                cliContext = state.cliContext.setProfiles(action.profiles.toList())
            )
            else -> state
        }
    }
}