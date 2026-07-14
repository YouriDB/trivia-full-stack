package com.trivia.app.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Question {
    
    @JsonProperty("type")
    public String type;

    @JsonProperty("difficulty")
    public String difficulty;

    @JsonProperty("category")
    public String category;

    @JsonProperty("question")
    public String question;

    @JsonProperty("correct_answer")
    public String correctAnswer;

    @JsonProperty("incorrect_answers")
    public List<String> incorrectAnswers;

}
