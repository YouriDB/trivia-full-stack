package com.trivia.app.models;

import java.io.Serializable;
import java.util.List;

public class SessionStartResponse implements Serializable {

    private String sessionId;
    private List<ClientQuestion> questions;

    public String getSessionId() {
        return sessionId;
    }
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public List<ClientQuestion> getQuestions() {
        return questions;
    }
    public void setQuestions(List<ClientQuestion> questions) {
        this.questions = questions;
    }


    
}
