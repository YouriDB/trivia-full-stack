package com.trivia.app.models;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

/**
 * Session used to save into Redis cache to later retrieve the client's session questions
 * default set TTL to 60 seconds if no expirationInSeconds is given.
 */
@RedisHash(timeToLive =  60L)
public class Session implements Serializable {

    private String sessionId;
    private List<Question> questions;
    // Make sure to remove data when session is over
    @TimeToLive
    private Long expirationInSeconds;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Long getExpirationInSeconds() {
        return expirationInSeconds;
    }

    public void setExpirationInSeconds(Long expirationInSeconds) {
        this.expirationInSeconds = expirationInSeconds;
    }
    
}
