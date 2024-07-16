package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppVersions(
    var validatorVrapperVersion: String,
    var validatorVersion: String,
)
