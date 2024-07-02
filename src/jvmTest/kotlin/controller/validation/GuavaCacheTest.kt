package controller.validation

import instrumentation.ValidationInstrumentation.compareValidationResponses
import instrumentation.ValidationInstrumentation.givenAValidationRequest
import instrumentation.ValidationInstrumentation.givenAValidationResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.hl7.fhir.validation.ValidationEngine
import org.hl7.fhir.validation.cli.services.SessionCache
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GuavaCacheTest {

    @Test
    fun `test happy path`() {
        val sessionCache: GuavaSessionCache =
            GuavaSessionCache(2)
        val sessionIds : Set<String> = sessionCache.getSessionIds();
        val engine1 : ValidationEngine = mockk()
        val sessionId1 = sessionCache.cacheSession(engine1);

        val engine2 : ValidationEngine = mockk()
        val sessionId2 = sessionCache.cacheSession(engine2);

        assert(sessionCache.sessionIds.size == 2)

        val engine3 : ValidationEngine = mockk()
        val sessionId3 = sessionCache.cacheSession(engine3);

        assert(sessionCache.sessionIds.size == 2)
        assert(sessionCache.sessionExists(sessionId2));
        assert(sessionCache.sessionExists(sessionId3));

        sessionCache.fetchSessionValidatorEngine(sessionId2);

        val engine4 : ValidationEngine = mockk()
        val sessionId4 = sessionCache.cacheSession(engine4);

        assert(sessionCache.sessionIds.size == 2)
        assert(sessionCache.sessionExists(sessionId2));
        assert(sessionCache.sessionExists(sessionId4));

    }
}