package controller.validation

import org.hl7.fhir.validation.service.ValidationService
import java.util.concurrent.atomic.AtomicReference

object ValidationServiceStatus {
    private val serviceRef = AtomicReference<ValidationService?>(null)

    fun setService(service: ValidationService) {
        serviceRef.set(service)
    }

    fun isReady(): Boolean = serviceRef.get() != null
}
