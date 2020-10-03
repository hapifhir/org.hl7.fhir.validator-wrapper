package model

import kotlinx.serialization.Serializable

@Serializable
data class FhirVersionsResponse(var versions: MutableList<String> = mutableListOf())