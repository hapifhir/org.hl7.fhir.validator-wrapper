package instrumentation

import org.hl7.fhir.utilities.npm.PackageInfo

object IgInstrumentation {

    val packageInfoA = listOf<PackageInfo>(
        PackageInfo(null, null, null, null, "https://www.youtube.com/user/MarbleHornets/videos", null, null),
        PackageInfo(null, null, null, null, "https://www.youtube.com/c/lockpickinglawyer/videos", null, null),
        PackageInfo(null, null, null, null, "https://www.youtube.com/c/theneedledrop/videos", null, null),
        PackageInfo(null, null, null, null, "https://www.youtube.com/c/GavinWebber/videos", null, null),
    )

    val packageInfoB = listOf<PackageInfo>(
        PackageInfo(null, null, null, null, "https://www.youtube.com/c/TechnologyConnections/videos",null, null),
        PackageInfo(null, null, null, null, "https://www.youtube.com/c/OrdinarySausage/videos", null, null),
        PackageInfo(null, null, null, null, "https://www.youtube.com/c/Electroboom/videos", null, null),
        PackageInfo(null, null, null, null, "https://www.youtube.com/c/ThisOldTony/videos", null, null)
    )

    val modelPackageInfoA = listOf<model.PackageInfo>(
        model.PackageInfo(url = "https://www.youtube.com/user/MarbleHornets/videos"),
        model.PackageInfo(url = "https://www.youtube.com/c/lockpickinglawyer/videos"),
        model.PackageInfo(url = "https://www.youtube.com/c/theneedledrop/videos"),
        model.PackageInfo(url = "https://www.youtube.com/c/GavinWebber/videos")
    )

    val modelPackageInfoB = listOf<model.PackageInfo>(
        model.PackageInfo(url = "https://www.youtube.com/c/TechnologyConnections/videos"),
        model.PackageInfo(url = "https://www.youtube.com/c/OrdinarySausage/videos"),
        model.PackageInfo(url = "https://www.youtube.com/c/Electroboom/videos"),
        model.PackageInfo(url = "https://www.youtube.com/c/ThisOldTony/videos")
    )

    val simplifierPackageInfoDSTU2 = listOf<PackageInfo>(
        PackageInfo("A", null, null, null, null, null, null),
        PackageInfo("B", null, null, null, null, null, null),
        PackageInfo("C", null, null, null, null, null, null),
        PackageInfo("D", null, null, null, null, null, null)
    )

    val simplifierModelPackageInfoDSTU2 = listOf<model.PackageInfo>(
        model.PackageInfo(id = "A"),
        model.PackageInfo(id = "B"),
        model.PackageInfo(id = "C"),
        model.PackageInfo(id = "D")
    )

    val simplifierPackageInfoSTU3 = listOf<PackageInfo>(
        PackageInfo("E", null, null, null, null, null, null),
        PackageInfo("F", null, null, null, null, null, null),
        PackageInfo("G", null, null, null, null, null, null),
        PackageInfo("H", null, null, null, null, null, null)
    )

    val simplifierModelPackageInfoSTU3 = listOf<model.PackageInfo>(
        model.PackageInfo(id = "E"),
        model.PackageInfo(id = "F"),
        model.PackageInfo(id = "G"),
        model.PackageInfo(id = "H")
    )

    val simplifierPackageInfoR4 = listOf<PackageInfo>(
        PackageInfo("I", null, null, null, null, null, null),
        PackageInfo("J", null, null, null, null, null, null),
        PackageInfo("K", null, null, null, null, null, null),
        PackageInfo("L", null, null, null, null, null, null)
    )

    val simplifierModelPackageInfoR4 = listOf<model.PackageInfo>(
        model.PackageInfo(id = "I"),
        model.PackageInfo(id = "J"),
        model.PackageInfo(id = "K"),
        model.PackageInfo(id = "L")
    )

    fun givenAReturnedListOfValidPackageInfoA(): MutableList<PackageInfo> {
        return packageInfoA.toMutableList()
    }

    fun givenAProcessedListOfValidPackageInfoA(): MutableList<model.PackageInfo> {
        return modelPackageInfoA.toMutableList()
    }

    fun givenAReturnedListOfValidPackageInfoDSTU2(): MutableList<PackageInfo> {
        return simplifierPackageInfoDSTU2.toMutableList()
    }

    fun givenAProcessedListOfValidPackageInfoDSTU2(): MutableList<model.PackageInfo> {
        return simplifierModelPackageInfoDSTU2.toMutableList()
    }

    fun givenAReturnedListOfValidPackageInfoSTU3(): MutableList<PackageInfo> {
        return simplifierPackageInfoSTU3.toMutableList()
    }

    fun givenAProcessedListOfValidPackageInfoSTU3(): MutableList<model.PackageInfo> {
        return simplifierModelPackageInfoSTU3.toMutableList()
    }

    fun givenAReturnedListOfValidPackageInfoR4(): MutableList<PackageInfo> {
        return simplifierPackageInfoR4.toMutableList()
    }

    fun givenAProcessedListOfValidPackageInfoR4(): MutableList<model.PackageInfo> {
        return simplifierModelPackageInfoR4.toMutableList()
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