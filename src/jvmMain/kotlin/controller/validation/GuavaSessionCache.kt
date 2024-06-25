package controller.validation;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.hl7.fhir.validation.ValidationEngine;
import org.hl7.fhir.validation.cli.services.SessionCache;

import java.util.Set;
import java.util.UUID;

public class GuavaSessionCache implements SessionCache {

    Cache<String, ValidationEngine> cache = CacheBuilder.newBuilder().maximumSize(3).build();

    @Override
    public String cacheSession(ValidationEngine validationEngine) {
        String generatedId = generateID();
        cache.put(generatedId, validationEngine);
        return generatedId;
    }

    @Override
    public String cacheSession(String sessionId, ValidationEngine validationEngine) {
        if(sessionId == null) {
            sessionId = cacheSession(validationEngine);
        } else {
            cache.put(sessionId, validationEngine);
        }
        return sessionId;
    }

    @Override
    public boolean sessionExists(String sessionKey) {
        return cache.getIfPresent(sessionKey) != null;
    }

    @Override
    public ValidationEngine removeSession(String s) {
       return null;
    }

    @Override
    public ValidationEngine fetchSessionValidatorEngine(String sessionKey) {
        return cache.getIfPresent(sessionKey);
    }

    @Override
    public Set<String> getSessionIds() {
        return cache.asMap().keySet();
    }

    /**
     * Session ids generated internally are UUID {@link String}.
     * @return A new {@link String} session id.
     */
    private String generateID() {
        return UUID.randomUUID().toString();
    }
}
