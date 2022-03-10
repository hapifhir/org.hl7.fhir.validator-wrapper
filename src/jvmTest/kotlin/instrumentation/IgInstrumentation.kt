package instrumentation

import org.hl7.fhir.utilities.npm.PackageInfo

object IgInstrumentation {

    val packageInfoA = listOf<PackageInfo>(
        PackageInfo(null, null, null, null, "https://www.youtube.com/user/MarbleHornets/videos", null),
        PackageInfo(null, null, null, null, "https://www.youtube.com/c/lockpickinglawyer/videos", null),
        PackageInfo(null, null, null, null, "https://www.youtube.com/c/theneedledrop/videos", null),
        PackageInfo(null, null, null, null, "https://www.youtube.com/c/GavinWebber/videos", null),
    )

    val packageInfoB = listOf<PackageInfo>(
        PackageInfo(null, null, null, null, "https://www.youtube.com/c/TechnologyConnections/videos",null),
        PackageInfo(null, null, null, null, "https://www.youtube.com/c/OrdinarySausage/videos", null),
        PackageInfo(null, null, null, null, "https://www.youtube.com/c/Electroboom/videos", null),
        PackageInfo(null, null, null, null, "https://www.youtube.com/c/ThisOldTony/videos", null)
    )

    val modelPackageInfoA = listOf<model.PackageInfo>(
        model.PackageInfo(url = "https://www.youtube.com/user/MarbleHornets/videos"),
        model.PackageInfo(url = "https://www.youtube.com/c/lockpickinglawyer/videos"),
        model.PackageInfo(url = "https://www.youtube.com/c/theneedledrop/videos"),
        model.PackageInfo(url = "https://www.youtube.com/c/GavinWebber/videos"),
    )

    val modelPackageInfoB = listOf<model.PackageInfo>(
        model.PackageInfo(url = "https://www.youtube.com/c/TechnologyConnections/videos"),
        model.PackageInfo(url = "https://www.youtube.com/c/OrdinarySausage/videos"),
        model.PackageInfo(url = "https://www.youtube.com/c/Electroboom/videos"),
        model.PackageInfo(url = "https://www.youtube.com/c/ThisOldTony/videos")
    )

    fun givenAReturnedListOfValidPackageInfo(): MutableList<PackageInfo> {
        return packageInfoA.toMutableList()
    }

    fun givenAProcessedListOfValidPackageInfo(): MutableList<model.PackageInfo> {
        return modelPackageInfoA.toMutableList()
    }

    fun givenANullReturnedListOfPackageInfo(): MutableList<PackageInfo>? {
        return null
    }

    fun givenAnEmptyReturnedListOfPackageInfo(): MutableList<PackageInfo> {
        return mutableListOf()
    }

    fun givenAListOfValidIgUrlsA(): MutableList<model.PackageInfo> {
        return modelPackageInfoA.toMutableList()
    }

    fun givenAListOfValidIgUrlsB(): MutableList<model.PackageInfo> {
        return modelPackageInfoA.toMutableList()
    }

    fun givenAnEmptyListOfIgUrls(): MutableList<model.PackageInfo> {
        return mutableListOf<model.PackageInfo>()
    }
}