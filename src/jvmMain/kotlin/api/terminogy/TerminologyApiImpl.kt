package api.terminogy

import com.fasterxml.jackson.databind.DeserializationFeature
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.jackson.*

import model.CapabilityStatement

const val CONFORMANCE_ENDPOINT = "metadata?_summary=true"

class TerminologyApiImpl : TerminologyApi {

    private val client = HttpClient(CIO) {
        configureJson()
        configureLogging()
    }

    private fun HttpClientConfig<*>.configureJson() {
        /*install(JsonFeature) {
            serializer = JacksonSerializer {
                configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            }
        }*/
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

    override suspend fun getCapabilityStatement(serverUrl: String): CapabilityStatement {
        val metadataEndpoint = if (serverUrl.endsWith('/')) {
            serverUrl + CONFORMANCE_ENDPOINT
        } else {
            "${serverUrl}/$CONFORMANCE_ENDPOINT"
        }
        return client.get(metadataEndpoint)
    }
}