package api

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNull

class HttpClientProxyConfigTest {

    @Test
    fun `prefers uppercase HTTP proxy when multiple proxy variables are configured`() {
        val proxyUrl = resolveHttpClientProxyUrl(
            mapOf(
                "HTTP_PROXY" to "http://proxy.example:8080",
                "http_proxy" to "http://lowercase-proxy.example:8080",
                "HTTPS_PROXY" to "http://secure-proxy.example:8080",
                "https_proxy" to "http://secure-lowercase-proxy.example:8080"
            )
        )

        assertEquals("http://proxy.example:8080", proxyUrl)
    }

    @ParameterizedTest
    @ValueSource(strings = ["HTTP_PROXY", "http_proxy", "HTTPS_PROXY", "https_proxy"])
    fun `supports all proxy environment variable names`(variableName: String) {
        assertEquals(
            "http://proxy.example:8080",
            resolveHttpClientProxyUrl(mapOf(variableName to "http://proxy.example:8080"))
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
