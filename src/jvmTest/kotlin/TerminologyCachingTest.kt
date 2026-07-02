import org.hl7.fhir.r5.terminologies.client.TerminologyClientContext
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class TerminologyCachingTest {

    @AfterEach
    fun resetTerminologyClientCaching() {
        TerminologyClientContext.setCanUseCacheId(false)
    }

    @Test
    fun `terminology client caching is enabled during application startup`() {
        TerminologyClientContext.setCanUseCacheId(false)

        configureTerminologyClientCaching()

        assertTrue(TerminologyClientContext.isCanUseCacheId())
    }
}
