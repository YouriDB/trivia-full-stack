package com.trivia.app.models;

import java.io.Serializable;

public class ClientAnswer implements Serializable {

    private String question;    
    private String answer;

    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
}
