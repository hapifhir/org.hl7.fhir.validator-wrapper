package controller.version

interface VersionController {
    suspend fun listSupportedVersions(): MutableList<String>
}