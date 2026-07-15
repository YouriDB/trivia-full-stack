package com.trivia.app.models;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * SessionEndResponse used as a serializable JSON object to return the client
 * the grading of how many questions and answers they got right or wrong.
 */
public class SessionEndResponse implements Serializable {
    
    private Map<String, Boolean> grading;

    public Map<String, Boolean> getGrading() {
        return grading;
    }

    public void setGrading(List<Question> questions, List<ClientAnswer> answers) {
        grading = new Hashtable<>();

        Map<String, String> correctAnswerMap  = new Hashtable<>();
        // Complexity O(n) for n questions
        for (Question question : questions) {
            if (question.question == null | question.correctAnswer == null) {
                throw new IllegalArgumentException("Question or answer cannot be null");
            }
            correctAnswerMap.put(question.question, question.correctAnswer);
        }

        // Complexity O(n) for n answers
        for (ClientAnswer answer : answers) {
            if (answer.getQuestion() == null | answer.getAnswer() == null) {
                throw new IllegalArgumentException("Question or answer cannot be null");
            }

            // Check whether the answer was correct with the hashtable
            boolean answerCorrect = (correctAnswerMap.get(answer.getQuestion()) == answer.getAnswer());
            grading.put(answer.getQuestion(), answerCorrect);
        }
        
    }

}
