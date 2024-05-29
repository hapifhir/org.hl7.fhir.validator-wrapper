package controller.validation

import com.typesafe.config.ConfigFactory
import constants.Preset
import io.ktor.server.config.*
import model.PackageInfo
import org.hl7.fhir.validation.cli.services.PassiveExpiringSessionCache
import org.hl7.fhir.validation.cli.services.SessionCache
import org.hl7.fhir.validation.cli.services.ValidationService
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread


private const val SESSION_DEFAULT_DURATION: Long = 60


class ValidationServiceFactoryImpl : ValidationServiceFactory {
    private var validationService: ValidationService

    init {
        validationService = createValidationServiceInstance();

    }

    fun createValidationServiceInstance(): ValidationService {
        val sessionCacheDuration = System.getenv("SESSION_CACHE_DURATION")?.toLong() ?: SESSION_DEFAULT_DURATION;
        val sessionCache: SessionCache =
            PassiveExpiringSessionCache(sessionCacheDuration, TimeUnit.MINUTES).setResetExpirationAfterFetch(true);
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
        if (java.lang.Runtime.getRuntime().freeMemory() < ValidatorApplicationConfig.config.engineReloadThreshold) {
            println("Free memory ${java.lang.Runtime.getRuntime().freeMemory()} is less than ${ValidatorApplicationConfig.config.engineReloadThreshold}. Re-initializing validationService");
            validationService = createValidationServiceInstance();
        }
        return validationService;
    }
}