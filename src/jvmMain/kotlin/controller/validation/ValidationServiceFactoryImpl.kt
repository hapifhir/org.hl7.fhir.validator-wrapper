package controller.validation

import org.hl7.fhir.utilities.TimeTracker
import org.hl7.fhir.utilities.VersionUtilities
import org.hl7.fhir.validation.ValidationEngine
import org.hl7.fhir.validation.cli.model.CliContext
import org.hl7.fhir.validation.cli.services.PassiveExpiringSessionCache
import org.hl7.fhir.validation.cli.services.SessionCache
import org.hl7.fhir.validation.cli.services.ValidationService
import java.util.concurrent.TimeUnit

private const val MIN_FREE_MEMORY = 250000000
private const val SESSION_DEFAULT_DURATION: Long = 60

class ValidationServiceFactoryImpl : ValidationServiceFactory {
    @Volatile private var validationService: ValidationService = createValidationServiceInstance()
    @Volatile private var sessionCache: SessionCache = createSessionCacheInstance()

    private fun createSessionCacheInstance(): SessionCache {
        val sessionCacheDuration = System.getenv("SESSION_CACHE_DURATION")?.toLong() ?: SESSION_DEFAULT_DURATION
        return PassiveExpiringSessionCache(sessionCacheDuration, TimeUnit.MINUTES).setResetExpirationAfterFetch(true)
    }
    private fun createValidationServiceInstance() : ValidationService {
        sessionCache = createSessionCacheInstance()
        return ValidationService(sessionCache)
    }

    override fun getValidationEngine(cliContext: CliContext): ValidationEngine? {
        var definitions = "hl7.fhir.r5.core#current"
        if ("dev" != cliContext.sv) {
            definitions =
                VersionUtilities.packageForVersion(cliContext.sv) + "#" +
                        VersionUtilities.getCurrentVersion(cliContext.sv)
        }

        var validationEngine = sessionCache.fetchSessionValidatorEngine(definitions)
        if (validationEngine == null) {
            validationEngine = getValidationService().initializeValidator(cliContext, definitions, TimeTracker())
            sessionCache.cacheSession(definitions, validationEngine)
        }

        return validationEngine
    }
   
    override fun getValidationService() : ValidationService {
        if (java.lang.Runtime.getRuntime().freeMemory() < MIN_FREE_MEMORY) {
            println("Free memory ${java.lang.Runtime.getRuntime().freeMemory()} is less than ${MIN_FREE_MEMORY}. Re-initializing validationService");
            validationService = createValidationServiceInstance()
        }
        return validationService;
    }
}