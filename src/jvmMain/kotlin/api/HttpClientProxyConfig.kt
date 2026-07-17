package api

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.engine.cio.*
import io.ktor.http.*

internal fun resolveHttpClientProxyUrl(environment: Map<String, String> = System.getenv()): String? {
    return listOf("HTTP_PROXY", "http_proxy", "HTTPS_PROXY", "https_proxy")
        .firstNotNullOfOrNull { key -> environment[key]?.trim()?.takeIf { it.isNotEmpty() } }
}

internal fun HttpClientConfig<CIOEngineConfig>.configureProxyFromEnvironment(
    environment: Map<String, String> = System.getenv()
) {
    resolveHttpClientProxyUrl(environment)?.let { proxyUrl ->
        engine {
            proxy = ProxyBuilder.http(Url(proxyUrl))
        }
    }
}
