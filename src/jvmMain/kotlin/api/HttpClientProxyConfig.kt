package api

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.engine.cio.*
import io.ktor.http.*
import java.net.URI
import java.net.URISyntaxException

internal fun resolveHttpClientProxyUrl(environment: Map<String, String> = System.getenv()): String? {
    return listOf("HTTP_PROXY", "http_proxy", "HTTPS_PROXY", "https_proxy")
        .firstNotNullOfOrNull { key -> environment[key]?.trim()?.takeIf { it.isNotEmpty() } }
}

internal fun parseHttpClientProxyUrl(proxyUrl: String): Url {
    val uri = try {
        URI(proxyUrl)
    } catch (_: URISyntaxException) {
        throw IllegalArgumentException("Configured HTTP proxy URL is malformed")
    }

    require(uri.scheme.equals("http", ignoreCase = true) || uri.scheme.equals("https", ignoreCase = true)) {
        "Configured HTTP proxy must use the http or https scheme"
    }
    require(!uri.host.isNullOrBlank()) {
        "Configured HTTP proxy must include a hostname"
    }

    val url = try {
        Url(proxyUrl)
    } catch (_: URLParserException) {
        throw IllegalArgumentException("Configured HTTP proxy URL is malformed")
    }

    return url
}

internal fun HttpClientConfig<CIOEngineConfig>.configureProxyFromEnvironment(
    environment: Map<String, String> = System.getenv()
) {
    resolveHttpClientProxyUrl(environment)?.let { proxyUrl ->
        engine {
            proxy = ProxyBuilder.http(parseHttpClientProxyUrl(proxyUrl))
        }
    }
}
