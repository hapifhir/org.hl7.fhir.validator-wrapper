package controller.validation

import org.hl7.fhir.validation.service.SessionCache

interface SessionCacheFactory {
    fun getSessionCache(): SessionCache
}