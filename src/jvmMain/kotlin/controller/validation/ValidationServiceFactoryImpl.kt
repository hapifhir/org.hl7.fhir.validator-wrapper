package controller.validation

import java.util.concurrent.TimeUnit;

import org.hl7.fhir.validation.cli.services.ValidationService
import org.hl7.fhir.validation.cli.services.SessionCache

private const val MIN_FREE_MEMORY = 250000000
private const val SESSION_DEFAULT_DURATION: Long = 60

class ValidationServiceFactoryImpl : ValidationServiceFactory {
    private var validationService: ValidationService

    init {
        validationService = createValidationServiceInstance();
    }

     fun createValidationServiceInstance() : ValidationService {
        val sessionCacheDuration = System.getenv("SESSION_CACHE_DURATION")?.toLong() ?: SESSION_DEFAULT_DURATION;
        val sessionCache = SessionCache(sessionCacheDuration, TimeUnit.MINUTES);
        sessionCache.setExpirationAfterAccess(true);
        return ValidationService(sessionCache);
    }
   
    override fun getValidationService() : ValidationService {
        if (java.lang.Runtime.getRuntime().freeMemory() < MIN_FREE_MEMORY) {
            println("Free memory ${java.lang.Runtime.getRuntime().freeMemory()} is less than ${MIN_FREE_MEMORY}. Re-initializing validationService");
            validationService = createValidationServiceInstance();
        }
        return validationService;
    }
}