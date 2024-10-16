package controller.validation

import org.hl7.fhir.validation.cli.services.PassiveExpiringSessionCache
import org.hl7.fhir.validation.cli.services.SessionCache
import java.util.concurrent.TimeUnit

private const val SESSION_CACHE_DURATION_ENV_KEY = "SESSION_CACHE_DURATION"
private const val SESSION_DEFAULT_DURATION: Long = 60

private const val SESSION_CACHE_SIZE_ENV_KEY = "SESSION_CACHE_SIZE"
private const val SESSION_DEFAULT_SIZE: Long = 4

private const val SESSION_CACHE_IMPLEMENTATION_ENV_KEY = "SESSION_CACHE_IMPLEMENTATION"

private const val GUAVA_SESSION_CACHE_IMPLEMENTATION = "GuavaSessionCacheAdapter"
private const val PASSIVE_EXPIRING_SESSION_CACHE_IMPLEMENTATION = "PassiveExpiringSessionCache"

private const val SESSION_DEFAULT_CACHE_IMPLEMENTATION: String = GUAVA_SESSION_CACHE_IMPLEMENTATION

class SessionCacheFactoryImpl : SessionCacheFactory {

    override fun getSessionCache(): SessionCache {
        /* Session cache configuration from environment variables.
         *
         * SESSION_CACHE_IMPLEMENTATION can be either the deprecated PassiveExpiringSessionCache or the preferred
         * GuavaSessionCacheAdapter, and will be GuavaSessionCacheAdapter if unspecified.
         *
         * SESSION_CACHE_DURATION is the duration in minutes that a session will be kept in the cache. If negative,
         * sessions will not expire. If unspecified, the default is 60 minutes.
         *
         * SESSION_CACHE_SIZE (only available in GuavaSessionCacheAdapter) is the maximum number of sessions that will
         * be kept in the cache in last accessed last out order. If unspecified, the default is 4.
         *
         * TODO this should be encapsulated in a session cache configuration class, and use a cleaner method of
         *  configuration. These env vars are overloaded, and should be split per cache implementation.
         */
        val sessionCacheDuration = System.getenv(SESSION_CACHE_DURATION_ENV_KEY)?.toLong() ?: SESSION_DEFAULT_DURATION;
        val sessionCacheSize = System.getenv(SESSION_CACHE_SIZE_ENV_KEY)?.toLong() ?: SESSION_DEFAULT_SIZE
        val sessionCacheImplementation =
            System.getenv(SESSION_CACHE_IMPLEMENTATION_ENV_KEY) ?: SESSION_DEFAULT_CACHE_IMPLEMENTATION;
        val sessionCache: SessionCache =
            if (sessionCacheImplementation == PASSIVE_EXPIRING_SESSION_CACHE_IMPLEMENTATION) {
                PassiveExpiringSessionCache(sessionCacheDuration, TimeUnit.MINUTES).setResetExpirationAfterFetch(true);
            } else {
                GuavaSessionCacheAdapter(sessionCacheSize, sessionCacheDuration)
            }
        return sessionCache
    }
}