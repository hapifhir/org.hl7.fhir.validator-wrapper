package model

import org.hl7.fhir.r5.model.CanonicalType

data class CapabilityStatement(var instantiates: MutableList<CanonicalType?>? = mutableListOf())
