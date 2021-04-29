package api.terminogy

import com.fasterxml.jackson.databind.DeserializationFeature
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import model.CapabilityStatement

const val CONFORMANCE_ENDPOINT = "metadata?_summary=true"

class TerminologyApiImpl : TerminologyApi {

    private val client = HttpClient {
        configureJson()
        configureLogging()
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

    override suspend fun getCapabilityStatement(serverUrl: String): CapabilityStatement {
        val metadataEndpoint = if (serverUrl.endsWith('/')) {
            serverUrl + CONFORMANCE_ENDPOINT
        } else {
            "${serverUrl}/$CONFORMANCE_ENDPOINT"
        }
        return client.get(metadataEndpoint)
    }
}