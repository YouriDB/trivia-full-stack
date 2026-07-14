package com.trivia.app.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClientQuestion implements Serializable {
    
    private String question;
    private List<String> answers;

    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    
    public List<String> getAnswers() {
        return answers;
    }
    public void setAnswers(String correctAnswer, List<String> incorrectAnswers) {
        this.answers = new ArrayList<>();
        this.answers.add(correctAnswer);
        this.answers.addAll(incorrectAnswers);
    }

}
