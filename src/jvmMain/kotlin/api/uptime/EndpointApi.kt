package api.uptime

import java.io.File

interface EndpointApi {
    suspend fun getFileAtEndpoint(serverUrl: String): ByteArray
}