package api.uptime

import com.fasterxml.jackson.databind.DeserializationFeature
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*

import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.jackson.*

const val ENDPOINT_API_TIMEOUT: Long = 10000;

class EndpointApiImpl : EndpointApi {

    private val client = HttpClient (CIO) {
        configureJson()
        configureLogging()
        configureTimeout()
    }

    private fun HttpClientConfig<*>.configureJson() {
        install(ContentNegotiation) {
            jackson {
                disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            }
        }
    }

    private fun HttpClientConfig<*>.configureLogging() {
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
    }

    private fun HttpClientConfig<*>.configureTimeout() {
        install(HttpTimeout) {
            requestTimeoutMillis = ENDPOINT_API_TIMEOUT
        }
    }

    override suspend fun getFileAtEndpoint(serverUrl: String): ByteArray {
        return client.get(serverUrl).body()
    }
}