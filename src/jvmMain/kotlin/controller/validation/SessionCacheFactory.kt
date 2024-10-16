package controller.validation

import org.hl7.fhir.validation.cli.services.SessionCache

interface SessionCacheFactory {
    fun getSessionCache(): SessionCache
}