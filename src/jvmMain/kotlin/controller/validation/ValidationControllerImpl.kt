package controller.validation

import model.ValidationResponse
import org.hl7.fhir.validation.cli.utils.VersionUtil
import org.hl7.fhir.validation.cli.model.ValidationRequest
import org.hl7.fhir.validation.cli.services.ValidationService
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.io.File
import java.io.FileInputStream
import java.util.*
import model.CliContext
import org.hl7.fhir.utilities.TimeTracker;

class ValidationControllerImpl : ValidationController, KoinComponent {

    private val validationService by inject<ValidationService>()
    private val sessions : HashMap<String,String> = HashMap<String,String>()
    private var defaultSession: String = ""
    
    override fun initSession(ig: String): String {
        if (sessions.containsKey(ig)) {
            val sId = sessions[ig]
            if (sId != null) {
                println("Found existing session: ${sId}")
                defaultSession = sId
                return sId
            }
            
        }
        val context = CliContext()
        context.setTargetVer("4.0.1")
        context.setSv("4.0.1")
        context.addIg("us.nlm.vsac#0.3.0")
        context.addIg(ig)
        val sessionId = validationService.initializeValidator(context, ig, TimeTracker(), null)
        sessions.put(ig, sessionId)
        defaultSession = sessionId
        println("Generating session: ${sessionId}")
        return sessionId
    }

    override suspend fun validateRequest(validationRequest: ValidationRequest): ValidationResponse {
        return validationService.validateSources(validationRequest)
    }

    override suspend fun getValidatorVersion():String {
        return VersionUtil.getVersion()
    }

    override suspend fun getSessionId(ig: String): String? {
        val sessionId = sessions[ig]
        return sessionId
    }

    override suspend fun getDefaultSessionId(): String {
        return defaultSession
    }
}