package instrumentation

import org.hl7.fhir.utilities.npm.PackageInfo

object IgInstrumentation {

    fun givenAReturnedListOfValidPackageInfo(): MutableList<PackageInfo> {
        return mutableListOf<PackageInfo>(
            PackageInfo("null", "null", "null", "null", "https://www.youtube.com/user/MarbleHornets/videos", "null"),
            PackageInfo("null", "null", "null", "null", "https://www.youtube.com/c/lockpickinglawyer/videos", "null"),
            PackageInfo("null", "null", "null", "null", "https://www.youtube.com/c/theneedledrop/videos", "null"),
            PackageInfo("null", "null", "null", "null", "https://www.youtube.com/c/GavinWebber/videos", "null"),
            PackageInfo("null", "null", "null", "null", "https://www.youtube.com/c/TechnologyConnections/videos", "null"),
            PackageInfo("null", "null", "null", "null", "https://www.youtube.com/c/OrdinarySausage/videos", "null")
        )
    }

    fun givenANullReturnedListOfPackageInfo(): MutableList<PackageInfo>? {
        return null
    }

    fun givenAnEmptyReturnedListOfPackageInfo(): MutableList<PackageInfo> {
        return mutableListOf()
    }

    fun givenAListOfValidIgUrls(): MutableList<String> {
        return mutableListOf<String>(
            "https://www.youtube.com/user/MarbleHornets/videos",
            "https://www.youtube.com/c/lockpickinglawyer/videos",
            "https://www.youtube.com/c/theneedledrop/videos",
            "https://www.youtube.com/c/GavinWebber/videos",
            "https://www.youtube.com/c/TechnologyConnections/videos",
            "https://www.youtube.com/c/OrdinarySausage/videos"
        )
    }

    fun givenAnEmptyListOfIgUrls(): MutableList<String> {
        return mutableListOf<String>()
    }
}