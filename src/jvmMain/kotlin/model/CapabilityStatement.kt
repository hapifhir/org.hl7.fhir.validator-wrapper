package model

import org.hl7.fhir.r5.model.CanonicalType

/*TODO this should be using r5.model.CanonicalType for `instantiates`, BUT canonicalType needs some kotliny intervention
to work with fasterxml.jackson.databind because of 'Conflicting setter definitions for property "referenceElement"'.
This probably needs JSON annotations somewhere to disambiguate.
 */
data class CapabilityStatement(var instantiates: MutableList<String?>? = mutableListOf())
