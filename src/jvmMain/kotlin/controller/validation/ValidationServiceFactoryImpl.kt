package controller.validation

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import model.Preset
import org.hl7.fhir.validation.cli.services.SessionCache
import org.hl7.fhir.validation.cli.services.ValidationService
import java.io.File
import java.lang.reflect.Type
import kotlin.concurrent.thread


class ValidationServiceFactoryImpl : ValidationServiceFactory {
    private var sessionCacheFactory: SessionCacheFactory
    private var validationService: ValidationService
    private var presets: List<Preset>

    init {
        sessionCacheFactory = SessionCacheFactoryImpl()
        presets = loadPresets();
        validationService = createValidationServiceInstance();
    }

    fun createValidationServiceInstance(): ValidationService {
        val sessionCache: SessionCache = sessionCacheFactory.getSessionCache()

        val validationService = ValidationService(sessionCache);
        thread {
        presets.forEach {
            if (it.key != "CUSTOM") {
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

    fun loadPresets(): List<Preset> {
        try {
            val presetFile: String? = System.getenv("VALIDATOR_PRESETS")
            val fileContent: String?

            if (presetFile == null) {
                // default to resources/presets.json
                fileContent = this::class.java.classLoader.getResource("presets.json")?.readText()
            } else if (presetFile.equals("false", ignoreCase = true)) {
                return listOf()
            } else {
                println("Attempting to load presets from $presetFile")
                fileContent = File(presetFile).readText()
            }

            val presetListType: Type = object : TypeToken<ArrayList<Preset?>?>() {}.type
            return Gson().fromJson(fileContent, presetListType)
        } catch (e: Exception) {
            println("Error occurred loading presets. No presets will be loaded")
            e.printStackTrace()
            return listOf()
        }
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