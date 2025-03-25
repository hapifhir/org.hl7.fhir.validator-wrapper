package controller.validation

import ValidationServiceConfig
import ValidatorApplicationConfig
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.hl7.fhir.validation.cli.services.SessionCache
import org.hl7.fhir.validation.cli.services.ValidationService
import java.io.File
import java.lang.reflect.Type
import kotlin.concurrent.thread
import model.Preset
import java.util.*
import kotlin.collections.ArrayList

class ValidationServiceFactoryImpl : ValidationServiceFactory {
    private val validationServiceConfig: ValidationServiceConfig = ValidatorApplicationConfig.validationServiceConfig
    private var sessionCacheFactory: SessionCacheFactory = SessionCacheFactoryImpl()
    private var validationService: ValidationService
    private var presets: List<Preset>

    init {
        presets = loadPresets();
        validationService = createValidationServiceInstance();
    }

    private fun createValidationServiceInstance(): ValidationService {
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
        if (java.lang.Runtime.getRuntime().freeMemory() < validationServiceConfig.engineReloadThreshold) {
            println(
                "Free memory ${
                    java.lang.Runtime.getRuntime().freeMemory()
                } is less than ${validationServiceConfig.engineReloadThreshold}. Re-initializing validationService"
            );
            validationService = createValidationServiceInstance();
        }
        return validationService;
    }
}