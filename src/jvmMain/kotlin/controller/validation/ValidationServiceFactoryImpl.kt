package controller.validation

import constants.Preset
import org.hl7.fhir.validation.cli.services.SessionCache
import org.hl7.fhir.validation.cli.services.ValidationService
import kotlin.concurrent.thread

class ValidationServiceFactoryImpl : ValidationServiceFactory {
    private var sessionCacheFactory: SessionCacheFactory
    private var validationService: ValidationService

    init {
        sessionCacheFactory = SessionCacheFactoryImpl()
        validationService = createValidationServiceInstance();
    }

    fun createValidationServiceInstance(): ValidationService {
        val sessionCache: SessionCache = sessionCacheFactory.getSessionCache()

        val validationService = ValidationService(sessionCache);
        thread {
        Preset.values().forEach {
            if (it != Preset.CUSTOM) {
                println("Loading preset: " + it.key)
                try {
                    validationService.putBaseEngine(it.key, it.cliContext)
                } catch (e: Exception) {
                    println("Error loading preset: " + it.key)
                    e.printStackTrace()
                }
                println("Preset loaded: " + it.key);
            }
        }
        }
        return validationService
    }



    override fun getValidationService() : ValidationService {
        val engineReloadThreshold = (System.getenv("ENGINE_RELOAD_THRESHOLD") ?: "250000000").toLong()
        if (java.lang.Runtime.getRuntime().freeMemory() < engineReloadThreshold) {
            println("Free memory ${java.lang.Runtime.getRuntime().freeMemory()} is less than ${engineReloadThreshold}. Re-initializing validationService");
            validationService = createValidationServiceInstance();
        }
        return validationService;
    }
}