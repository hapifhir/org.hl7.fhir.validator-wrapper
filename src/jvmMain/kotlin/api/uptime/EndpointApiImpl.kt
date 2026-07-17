package api.uptime

import com.fasterxml.jackson.databind.DeserializationFeature
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*

import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*

const val ENDPOINT_API_TIMEOUT: Long = 10000;

internal fun resolveEndpointProxyUrl(environment: Map<String, String> = System.getenv()): String? {
    return listOf("HTTP_PROXY", "http_proxy", "HTTPS_PROXY", "https_proxy")
        .firstNotNullOfOrNull { key -> environment[key]?.trim()?.takeIf { it.isNotEmpty() } }
}

class EndpointApiImpl : EndpointApi {

    private val client = HttpClient (CIO) {
        resolveEndpointProxyUrl()?.let { proxyUrl ->
            engine {
                proxy = ProxyBuilder.http(Url(proxyUrl))
            }
        }
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
