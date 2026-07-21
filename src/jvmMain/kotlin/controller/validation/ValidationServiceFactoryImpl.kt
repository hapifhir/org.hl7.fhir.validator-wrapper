package controller.validation

import ValidationServiceConfig
import ValidatorApplicationConfig
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.hl7.fhir.validation.service.SessionCache
import org.hl7.fhir.validation.service.ValidationService
import java.io.File
import java.lang.reflect.Type
import kotlin.concurrent.thread
import model.Preset
import org.hl7.fhir.r5.terminologies.client.TerminologyClientContext
import java.util.*
import kotlin.collections.ArrayList

private const val TERMINOLOGY_CLIENT_USE_CACHE_ID = "TERMINOLOGY_CLIENT_USE_CACHE_ID"
private const val TERMINOLOGY_CLIENT_USE_CACHE_ID_DEFAULT = "TRUE"

class ValidationServiceFactoryImpl : ValidationServiceFactory {
    private val validationServiceConfig: ValidationServiceConfig = ValidatorApplicationConfig.validationServiceConfig
    private var sessionCacheFactory: SessionCacheFactory = SessionCacheFactoryImpl()
    @Volatile
    private var validationService: ValidationService
    private var presets: List<Preset>

    private val reloadLock = java.util.concurrent.locks.ReentrantLock()
    private var lastReloadTime: Long = 0
    private val RELOAD_COOLDOWN_MS = java.util.concurrent.TimeUnit.MINUTES.toMillis(5)

    init {
        presets = loadPresets();
        loadTerminologyContext();
        validationService = createValidationServiceInstance();
        lastReloadTime = System.currentTimeMillis()
    }

    private fun loadTerminologyContext() {
        val useCacheIdEnvValue =
            System.getenv(TERMINOLOGY_CLIENT_USE_CACHE_ID) ?: TERMINOLOGY_CLIENT_USE_CACHE_ID_DEFAULT
        val useCacheId : Boolean = useCacheIdEnvValue.uppercase().equals("TRUE")
        println("Terminology Client Initialized with useCacheId=$useCacheId")
        TerminologyClientContext.setCanUseCacheId(useCacheId)
    }

    // org.hl7.fhir.* deprecation is intentional pending upstream API updates
    @Suppress("DEPRECATION")
    private fun createValidationServiceInstance(): ValidationService {
        val sessionCache: SessionCache = sessionCacheFactory.getSessionCache()

        val validationService =
            ValidationService(sessionCache);
        thread {
        presets.forEach {
            if (it.key != "CUSTOM") {
                println("Loading preset: " + it.key)
                try {
                    validationService.putBaseEngine(it.key, it.validationContext)
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

    private fun loadPresets(): List<Preset> {
        try {
            val fileContent: String?

            if (validationServiceConfig.presetsFilePath.isNullOrEmpty()) {
                // default to resources/presets.json
                fileContent = this::class.java.classLoader.getResource("presets.json")?.readText()
            } else {
                println("Attempting to load presets from ${validationServiceConfig.presetsFilePath}")
                fileContent = File(validationServiceConfig.presetsFilePath).readText()
            }

            val presetListType: Type = object : TypeToken<ArrayList<Preset?>?>() {}.type
            return Gson().fromJson(fileContent, presetListType)
        } catch (e: Exception) {
            println("Error occurred loading presets. No presets will be loaded")
            e.printStackTrace()
            return listOf()
        }
    }

    override fun getValidationPresets(): List<Preset> {
        return Collections.unmodifiableList(presets)
    }

    override fun getValidationService() : ValidationService {
        val freeMemory = Runtime.getRuntime().freeMemory()
        if (freeMemory < validationServiceConfig.engineReloadThreshold) {
            val now = System.currentTimeMillis()
            // Try to acquire lock. If we can't, another thread is already reloading it, so just return the existing one.
            if (reloadLock.tryLock()) {
                try {
                    // Double check time and memory inside the lock
                    if (Runtime.getRuntime().freeMemory() < validationServiceConfig.engineReloadThreshold &&
                        (now - lastReloadTime) > RELOAD_COOLDOWN_MS) {

                        println(
                            "Free memory ${
                                Runtime.getRuntime().freeMemory()
                            } is less than ${validationServiceConfig.engineReloadThreshold} and cooldown passed. Re-initializing validationService"
                        );
                        //First create an empty validationService so the older one can be garbage collected before the
                        //new one is constructed.
                        validationService = createEmptyValidationServiceInstance()
                        //Then 'suggest' this might be a good time to do garbage collection
                        System.gc()
                        //Then build our new service instance.
                        validationService = createValidationServiceInstance()
                        lastReloadTime = System.currentTimeMillis()
                    }
                } finally {
                    reloadLock.unlock()
                }
            } else {
                println("Memory is low ($freeMemory), but another thread is currently re-initializing the engine. Skipping.")
            }
        }
        return validationService;
    }

    // New convenience function
    private fun createEmptyValidationServiceInstance() : ValidationService {
        val service = object : ValidationService() {};
        return service;
    }
}