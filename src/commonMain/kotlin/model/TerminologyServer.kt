package model

import kotlinx.serialization.Serializable

@Serializable
data class TerminologyServerRequest(var url: String)

@Serializable
data class TerminologyServerResponse(var url: String, val validTxServer: Boolean, val details: String = "")