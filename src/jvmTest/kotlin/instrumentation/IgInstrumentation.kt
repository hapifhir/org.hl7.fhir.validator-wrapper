package instrumentation

import org.hl7.fhir.utilities.npm.PackageInfo

object IgInstrumentation {

    fun givenAReturnedListOfValidPackageInfo(): MutableList<PackageInfo> {
        return mutableListOf<PackageInfo>(
            PackageInfo(null, null, null, null, "https://www.youtube.com/user/MarbleHornets/videos", null),
            PackageInfo(null, null, null, null, "https://www.youtube.com/c/lockpickinglawyer/videos", null),
            PackageInfo(null, null, null, null, "https://www.youtube.com/c/theneedledrop/videos", null),
            PackageInfo(null, null, null, null, "https://www.youtube.com/c/GavinWebber/videos", null),
            PackageInfo(null, null, null, null, "https://www.youtube.com/c/TechnologyConnections/videos",null),
            PackageInfo(null, null, null, null, "https://www.youtube.com/c/OrdinarySausage/videos", null)
        )
    }

    fun givenAProcessedListOfValidPackageInfo(): MutableList<model.PackageInfo> {
        return mutableListOf<model.PackageInfo>(
            model.PackageInfo(url = "https://www.youtube.com/user/MarbleHornets/videos"),
            model.PackageInfo(url = "https://www.youtube.com/c/lockpickinglawyer/videos"),
            model.PackageInfo(url = "https://www.youtube.com/c/theneedledrop/videos"),
            model.PackageInfo(url = "https://www.youtube.com/c/GavinWebber/videos"),
            model.PackageInfo(url = "https://www.youtube.com/c/TechnologyConnections/videos"),
            model.PackageInfo(url = "https://www.youtube.com/c/OrdinarySausage/videos")
        )
    }

    fun givenANullReturnedListOfPackageInfo(): MutableList<PackageInfo>? {
        return null
    }

    fun givenAnEmptyReturnedListOfPackageInfo(): MutableList<PackageInfo> {
        return mutableListOf()
    }

    fun givenAListOfValidIgUrls(): MutableList<model.PackageInfo> {
        return mutableListOf<model.PackageInfo>(
            model.PackageInfo(url = "https://www.youtube.com/user/MarbleHornets/videos"),
            model.PackageInfo("https://www.youtube.com/c/lockpickinglawyer/videos"),
            model.PackageInfo("https://www.youtube.com/c/theneedledrop/videos"),
            model.PackageInfo("https://www.youtube.com/c/GavinWebber/videos"),
            model.PackageInfo("https://www.youtube.com/c/TechnologyConnections/videos"),
            model.PackageInfo("https://www.youtube.com/c/OrdinarySausage/videos")
        )
    }

    fun givenAnEmptyListOfIgUrls(): MutableList<model.PackageInfo> {
        return mutableListOf<model.PackageInfo>()
    }
}