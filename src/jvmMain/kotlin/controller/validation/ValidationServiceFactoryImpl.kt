package controller.validation

import org.hl7.fhir.validation.ValidationEngine
import java.util.concurrent.TimeUnit;

import org.hl7.fhir.validation.cli.services.ValidationService
import org.hl7.fhir.validation.cli.services.SessionCache
import org.hl7.fhir.validation.cli.services.PassiveExpiringSessionCache

private const val MIN_FREE_MEMORY = 250000000
private const val SESSION_DEFAULT_DURATION: Long = 60

class ValidationServiceFactoryImpl : ValidationServiceFactory {
    private var validationService: ValidationService
    private var sessionCache: SessionCache

    init {
        sessionCache = createSessionCacheInstance()
        validationService = createValidationServiceInstance()
    }

    private fun createSessionCacheInstance(): SessionCache {
        val sessionCacheDuration = System.getenv("SESSION_CACHE_DURATION")?.toLong() ?: SESSION_DEFAULT_DURATION
        return PassiveExpiringSessionCache(sessionCacheDuration, TimeUnit.MINUTES).setResetExpirationAfterFetch(true)
    }
    private fun createValidationServiceInstance() : ValidationService {
        sessionCache = createSessionCacheInstance()
        return ValidationService(sessionCache)
    }

    override fun getCachedValidator(sessionId: String?) : ValidationEngine? {
        return if (sessionId != null && sessionCache.sessionExists(sessionId)) {
            sessionCache.fetchSessionValidatorEngine(sessionId)
        } else {
            null
        }
    }
   
    override fun getValidationService() : ValidationService {
        if (java.lang.Runtime.getRuntime().freeMemory() < MIN_FREE_MEMORY) {
            println("Free memory ${java.lang.Runtime.getRuntime().freeMemory()} is less than ${MIN_FREE_MEMORY}. Re-initializing validationService");
            validationService = createValidationServiceInstance()
        }
        return validationService;
    }
}