package model

import kotlinx.serialization.Serializable

@Serializable
data class AppVersions(
    var validatorWrapperVersion: String,
    var validatorVersion: String,
)
