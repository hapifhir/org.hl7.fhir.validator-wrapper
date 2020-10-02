package model

import kotlinx.serialization.Serializable

@Serializable
data class IGResponse(var igs: MutableList<String> = mutableListOf())