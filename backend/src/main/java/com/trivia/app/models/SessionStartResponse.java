package com.trivia.app.models;

import java.io.Serializable;
import java.util.List;

/**
 * SessionStartResponse will return to the client to use the sessionId for checking answers in the future
 * and to display the questions onto the front-end.
 */
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
