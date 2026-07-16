package com.trivia.app.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.trivia.app.models.Session;

/**
 * Interface to communicate with the Redis Cache.
 */
@Component
public class CacheHelper {

    private final String questionsCachePrefix = "QUESTIONS_SESSION_";

    private final RedisTemplate<String, Object> redisTemplate;

    public CacheHelper(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void storeSession(String sessionId, Session session) {
        Session cachedSession = (Session) redisTemplate.opsForValue().get(sessionId);
        if (cachedSession == null) {
            redisTemplate.opsForValue().set(questionsCachePrefix + sessionId, session);
        }
    }

    public Session getSession(String sessionId) {
        Session cachedSession = (Session) redisTemplate.opsForValue().get(questionsCachePrefix + sessionId);
        if (cachedSession == null) {
            return null;
        }
        
        return cachedSession;
    }

    public void deleteSession(String sessionId) {
        redisTemplate.delete(questionsCachePrefix + sessionId);
    }

}
