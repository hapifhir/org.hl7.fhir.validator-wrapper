package controller.validation

import constants.ANY_EXTENSION
import model.PackageInfo
import model.BundleValidationRule
import java.util.concurrent.TimeUnit;

import org.hl7.fhir.validation.cli.model.CliContext
import org.hl7.fhir.validation.cli.services.ValidationService
import org.hl7.fhir.validation.cli.services.SessionCache
import org.hl7.fhir.validation.cli.services.PassiveExpiringSessionCache

private const val MIN_FREE_MEMORY = 25000000
private const val SESSION_DEFAULT_DURATION: Long = 60

val IPS_IG = PackageInfo(
    "hl7.fhir.uv.ips",
    "1.1.0",
    "4.0.1",
    "http://hl7.org/fhir/uv/ips/STU1.1"
)

val IPS_CONTEXT = CliContext()
    .setBaseEngine("IPS")
    .setSv("4.0.1")
    .addIg(PackageInfo.igLookupString(IPS_IG))
    .setExtensions(listOf(ANY_EXTENSION))
    .setCheckIPSCodes(true)
    .setBundleValidationRules(listOf(
        BundleValidationRule()
            .setRule("Composition:0")
            .setProfile("http://hl7.org/fhir/uv/ips/StructureDefinition/Composition-uv-ips")
    ))

class ValidationServiceFactoryImpl : ValidationServiceFactory {
    private var validationService: ValidationService

    init {
        System.out.println("ValidationServiceFactoryImpl.init")
        validationService = createValidationServiceInstance();
    }

     fun createValidationServiceInstance() : ValidationService {
         System.out.println("createValidationServiceInstance")
         val sessionCacheDuration = System.getenv("SESSION_CACHE_DURATION")?.toLong() ?: SESSION_DEFAULT_DURATION;
        val sessionCache: SessionCache = PassiveExpiringSessionCache(sessionCacheDuration, TimeUnit.MINUTES).setResetExpirationAfterFetch(true);
        val validationService = ValidationService(sessionCache);
         validationService.putBaseEngine("IPS", IPS_CONTEXT)
         return validationService
    }
   
    override fun getValidationService() : ValidationService {
        if (java.lang.Runtime.getRuntime().freeMemory() < MIN_FREE_MEMORY) {
            println("Free memory ${java.lang.Runtime.getRuntime().freeMemory()} is less than ${MIN_FREE_MEMORY}. Re-initializing validationService");
            validationService = createValidationServiceInstance();
        }
        return validationService;
    }
}