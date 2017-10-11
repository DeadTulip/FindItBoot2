package p.hh.fiboot2.security.token;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.UUID;

public class TokenService {

    private static final Cache restApiAuthTokenCache = CacheManager.getInstance().getCache("restApiAuthTokenCache");
    public static final int HALF_AN_HOUR_IN_MILLISECONDS = 30 * 60 * 1000;

    @Scheduled(fixedRate = HALF_AN_HOUR_IN_MILLISECONDS)
    public void evictExpiredTokens() {
        System.out.println("Evicting expired tokens");
        restApiAuthTokenCache.evictExpiredElements();
    }

    public String generateNewToken() {
        return UUID.randomUUID().toString();
    }

    public void store(String token, Authentication authentication) {
        restApiAuthTokenCache.put(new Element(token, authentication));
    }

    public boolean contains(String token) {
        return restApiAuthTokenCache.get(token) != null;
    }

    public Authentication retrieve(String token) {
        return (Authentication) restApiAuthTokenCache.get(token).getObjectValue();
    }
}
