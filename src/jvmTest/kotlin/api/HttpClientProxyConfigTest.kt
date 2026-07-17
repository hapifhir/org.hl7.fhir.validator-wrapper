package api

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
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

    @Test
    fun `rejects malformed proxy URLs without exposing their value`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            parseHttpClientProxyUrl("http://user:secret@[invalid")
        }

        assertEquals("Configured HTTP proxy URL is malformed", exception.message)
        assertFalse(exception.message.orEmpty().contains("secret"))
        assertNull(exception.cause)
    }

    @Test
    fun `rejects proxy URLs with unsupported schemes`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            parseHttpClientProxyUrl("socks://proxy.example:1080")
        }

        assertEquals("Configured HTTP proxy must use the http or https scheme", exception.message)
    }

    @Test
    fun `rejects proxy URLs without a hostname`() {
        val exception = assertFailsWith<IllegalArgumentException> {
            parseHttpClientProxyUrl("http:///proxy")
        }

        assertEquals("Configured HTTP proxy must include a hostname", exception.message)
    }
}
