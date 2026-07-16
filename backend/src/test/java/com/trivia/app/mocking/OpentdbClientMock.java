package com.trivia.app.mocking;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.trivia.app.clients.OpentdbClient;
import com.trivia.app.models.ClientAnswer;
import com.trivia.app.models.Question;

@Component
public class OpentdbClientMock {
    
    // Used to mock the opentdb client
    private OpentdbClient opentdbClient;

    private Question question1, question2, question3;

    public OpentdbClientMock(OpentdbClient opentdbClient) {
        this.opentdbClient = opentdbClient;
    }

    public void mock3QuestionsAllAny() throws Exception {
        // Make fake questions
        question1 = new Question();
        question2 = new Question();
        question3 = new Question();

        question1.type = "multiple";
        question1.difficulty = "medium";
        question1.category = "General Knowledge";
        question1.question = "In which country was the 1992 Summer Olympics Games held?";
        question1.correctAnswer = "Spain";
        question1.incorrectAnswers = new ArrayList<>(Arrays.asList("Russia", "Korea", "USA"));

        question2.type = "boolean";
        question2.difficulty = "medium";
        question2.category = "General Knowledge";
        question2.question = "The commercial UK channel ITV stands for 'International Television'.";
        question2.correctAnswer = "False";
        question2.incorrectAnswers = new ArrayList<>(Arrays.asList("True"));

        question3.type = "multiple";
        question3.difficulty = "easy";
        question3.category = "General Knowledge";
        question3.question = "How many fingers does a single human hand have?";
        question3.correctAnswer = "Four";
        question3.incorrectAnswers = new ArrayList<>(Arrays.asList("Five", "Ten", "Eight"));


        // Mock for amount 3
        when(opentdbClient.getQuestions(3, null, null, null))
            .thenReturn(
                List.of(
                    question1,
                    question2,
                    question3
                )
            );
    }

    public List<ClientAnswer> get3CorrectAnswers() {
        ClientAnswer clientAnswer1 = new ClientAnswer();
        ClientAnswer clientAnswer2 = new ClientAnswer();
        ClientAnswer clientAnswer3 = new ClientAnswer();

        clientAnswer1.setQuestion(question1.question);
        clientAnswer2.setQuestion(question2.question);
        clientAnswer3.setQuestion(question3.question);

        clientAnswer1.setAnswer(question1.correctAnswer);
        clientAnswer2.setAnswer(question2.correctAnswer);
        clientAnswer3.setAnswer(question3.correctAnswer);

        return List.of(clientAnswer1, clientAnswer2, clientAnswer3);
    }

    public List<ClientAnswer> get3IncorrectAnswers() {
        ClientAnswer clientAnswer1 = new ClientAnswer();
        ClientAnswer clientAnswer2 = new ClientAnswer();
        ClientAnswer clientAnswer3 = new ClientAnswer();

        clientAnswer1.setQuestion(question1.question);
        clientAnswer2.setQuestion(question2.question);
        clientAnswer3.setQuestion(question3.question);

        clientAnswer1.setAnswer("wronganswer-1");
        clientAnswer2.setAnswer("wronganswer-1");
        clientAnswer3.setAnswer("wronganswer-1");

        return List.of(clientAnswer1, clientAnswer2, clientAnswer3);
    }

}
