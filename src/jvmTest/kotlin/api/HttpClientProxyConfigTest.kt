package api

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class HttpClientProxyConfigTest {

    @Test
    fun `uses the configured HTTP proxy`() {
        val proxyUrl = resolveHttpClientProxyUrl(
            mapOf(
                "HTTP_PROXY" to "http://proxy.example:8080",
                "HTTPS_PROXY" to "http://secure-proxy.example:8080"
            )
        )

        assertEquals("http://proxy.example:8080", proxyUrl)
    }

    @Test
    fun `supports lowercase and HTTPS proxy fallbacks`() {
        assertEquals(
            "http://lowercase-proxy.example:8080",
            resolveHttpClientProxyUrl(mapOf("http_proxy" to "http://lowercase-proxy.example:8080"))
        )
        assertEquals(
            "http://secure-proxy.example:8080",
            resolveHttpClientProxyUrl(mapOf("HTTPS_PROXY" to "http://secure-proxy.example:8080"))
        )
    }

    @Test
    fun `does not configure a proxy when variables are missing or blank`() {
        assertNull(resolveHttpClientProxyUrl(emptyMap()))
        assertNull(resolveHttpClientProxyUrl(mapOf("HTTP_PROXY" to "  ")))
    }
}
