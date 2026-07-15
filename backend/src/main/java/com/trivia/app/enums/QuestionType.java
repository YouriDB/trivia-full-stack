package com.trivia.app.enums;

public enum QuestionType {
    
    MultipleChoice ("multiple"),
    TrueFalse ("boolean");

    private final String value;

    QuestionType(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

}
