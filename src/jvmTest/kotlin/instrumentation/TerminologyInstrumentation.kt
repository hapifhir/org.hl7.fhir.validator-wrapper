package instrumentation

import model.CapabilityStatement
import org.hl7.fhir.r5.model.CanonicalType
import org.hl7.fhir.utilities.npm.PackageInfo

object TerminologyInstrumentation {

    fun givenACapabilityStatementToTest(): String {
        return "{ capability statement }"
    }

    fun givenATerminologyServerUrl(): String {
        return "http://tx.fhir.org"
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

    fun givenAValidCapabilityStatement(): CapabilityStatement {
        val capStmt = CapabilityStatement()
        val canonicalType = "http://hl7.org/fhir/CapabilityStatement/terminology-server"
        capStmt.instantiates?.add(canonicalType)
        return capStmt
    }

    fun givenAnInvalidCapabilityStatement(): CapabilityStatement {
        val capStmt = CapabilityStatement()
        val canonicalType = "https://tinyurl.com/3usj3yvc"
        capStmt.instantiates?.add(canonicalType)
        return capStmt
    }
}