package controller.validation

import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import org.hl7.fhir.validation.ValidationEngine
import org.hl7.fhir.validation.cli.services.SessionCache
import java.util.*

class GuavaSessionCache(cacheSize : Long) : SessionCache {
    private val cache: Cache<String, ValidationEngine> = CacheBuilder.newBuilder().maximumSize(cacheSize).build()

    override fun cacheSession(validationEngine: ValidationEngine): String {
        val generatedId = generateID()
        cache.put(generatedId, validationEngine)
        println("Cache size: " + cache.size())
        return generatedId
    }

    override fun cacheSession(sessionId: String?, validationEngine: ValidationEngine): String {
        var sessionIdVar = sessionId
        if (sessionIdVar == null) {
            sessionIdVar = cacheSession(validationEngine)
        } else {
            cache.put(sessionIdVar, validationEngine)
            println("Cache size: " + cache.size())
        }
        return sessionIdVar
    }

    override fun sessionExists(sessionKey: String?): Boolean {
       // if (sessionKey == null) {return false }
        return cache.asMap().containsKey(sessionKey)
    }

    override fun removeSession(s: String): ValidationEngine? {
        return null
    }

    override fun fetchSessionValidatorEngine(sessionKey: String): ValidationEngine? {
        return cache.getIfPresent(sessionKey)
    }

    override fun getSessionIds(): Set<String> {
        return cache.asMap().keys
    }

    /**
     * Session ids generated internally are UUID [String].
     * @return A new [String] session id.
     */
    private fun generateID(): String {
        return UUID.randomUUID().toString()
    }
}