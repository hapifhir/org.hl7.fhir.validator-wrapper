package controller.validation

import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import org.hl7.fhir.validation.ValidationEngine
import org.hl7.fhir.validation.service.SessionCache
import java.util.*
import java.util.concurrent.TimeUnit

class GuavaSessionCacheAdapter(cacheSize : Long, cacheDuration: Long) :
    SessionCache {
    private val cache: Cache<String, ValidationEngine>;
    init {
        val cacheBuilder = CacheBuilder.newBuilder()
        if (cacheDuration > 0) {
            cacheBuilder.expireAfterAccess(cacheDuration, TimeUnit.MINUTES)
        }
        if (cacheSize >= 0) {
            cacheBuilder.maximumSize(cacheSize)
        }
        cache = cacheBuilder.build<String, ValidationEngine>()
    }

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
        return cache.asMap().containsKey(sessionKey)
    }

    override fun fetchSessionValidatorEngine(sessionKey: String): ValidationEngine? {
        return cache.getIfPresent(sessionKey)
    }

    override fun getSessionIds(): Set<String> {
        return cache.asMap().keys
    }

    override fun cleanUp() {
        cache.cleanUp()
    }

    /**
     * Session ids generated internally are UUID [String].
     * @return A new [String] session id.
     */
    private fun generateID(): String {
        return UUID.randomUUID().toString()
    }
}