package model

import kotlinx.serialization.Serializable

@Serializable
actual class ValidationTime actual constructor() {

    private var terminology : Long = 0
    private var structureDefinition : Long = 0
    private var resourceParse : Long = 0
    private var fhirPath : Long = 0
    private var checkingSpecials : Long = 0
    private var overall : Long = 0

    actual fun getTerminology(): Long {
        return terminology;
    }

    actual fun setTerminology(terminology: Long): ValidationTime {
       this.terminology = terminology
        return this
    }

    actual fun getStructureDefinition(): Long {
        return structureDefinition
    }

    actual fun setStructureDefinition(structureDefinition: Long): ValidationTime {
        this.structureDefinition = structureDefinition
        return this
    }

    actual fun getResourceParse(): Long {
        return this.resourceParse
    }

    actual fun setResourceParse(resourceParse: Long): ValidationTime {
        this.resourceParse = resourceParse
        return this
    }

    actual fun getFhirPath(): Long {
       return this.fhirPath
    }

    actual fun setFhirPath(fhirPath: Long): ValidationTime {
       this.fhirPath = fhirPath
        return this
    }

    actual fun getCheckingSpecials(): Long {
       return this.checkingSpecials
    }

    actual fun setCheckingSpecials(checkingSpecials: Long): ValidationTime {
       this.checkingSpecials = checkingSpecials
        return this
    }

    actual fun getOverall(): Long {
       return this.overall
    }

    actual fun setOverall(overall: Long): ValidationTime {
        this.overall = overall
        return this
    }
}