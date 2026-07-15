package com.trivia.app.enums;

public enum QuestionDifficulty {
    
    Easy ("easy"),
    Medium ("medium"),
    Hard ("hard");

    private final String value;

    QuestionDifficulty(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

}
