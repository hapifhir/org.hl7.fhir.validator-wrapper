package api.uptime

import com.fasterxml.jackson.databind.DeserializationFeature
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*

const val ENDPOINT_API_TIMEOUT: Long = 10000;

class EndpointApiImpl : EndpointApi {

    private val client = HttpClient {
        configureJson()
        configureLogging()
        configureTimeout()
    }

    private fun HttpClientConfig<*>.configureJson() {
        install(JsonFeature) {
            serializer = JacksonSerializer {
                configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
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
        return client.get(serverUrl)
    }
}