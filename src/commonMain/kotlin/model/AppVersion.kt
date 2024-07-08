package model

import kotlinx.serialization.Serializable

@Serializable
data class AppVersion(
    var wrapperVersion: String,
    var coreVersion: String,
)
