package instrumentation

object VersionsInstrumentation {

    fun givenAListOfExpectedVersions(): String {
        return "1.0.2, 1.4.0, 3.0.2, 4.0.1"
    }

    fun givenANullListOfExpectedVersions(): String? {
        return null
    }

    fun givenAListOfSupportedVersions(): MutableList<String> {
        return mutableListOf("1.0.2", "1.4.0", "3.0.2", "4.0.1")
    }

    fun givenAnEmptyListOfSupportedVersions(): MutableList<String> {
        return mutableListOf()
    }
}