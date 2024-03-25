package controller.conversion

interface ConversionController {
    suspend fun convertRequest(content: String, type: String? = "json", version: String? = "5.0", toType: String? = type,
                               toVersion: String? = version, sessionId: String?): ConversionResponse
}

data class ConversionResponse(val response: String, val sessionId: String)