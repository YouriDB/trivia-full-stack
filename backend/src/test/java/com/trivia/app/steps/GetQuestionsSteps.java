package com.trivia.app.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.trivia.app.clients.OpentdbClient;
import com.trivia.app.models.ClientQuestion;
import com.trivia.app.models.Question;
import com.trivia.app.models.SessionStartResponse;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GetQuestionsSteps {
    
    
    // Mock the opentdb client
    private OpentdbClient opentdbClient;
    // Used to for integration tests
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private ResponseEntity<SessionStartResponse> sessionStartResponse;

    public GetQuestionsSteps(
        OpentdbClient opentdbClient,
        TestRestTemplate restTemplate
    ) {
        this.opentdbClient = opentdbClient;
        this.restTemplate = restTemplate;
    }

    @Given("the opentdb api returns questions")
    public void opentdbReturnsQuestions() throws Exception {

        // Make fake questions
        Question question1 = new Question();
        Question question2 = new Question();
        Question question3 = new Question();

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

    @When("the client requests questions from {string}")
    public void clientRequestsQuestions(String path) {
        this.sessionStartResponse = restTemplate.getForEntity(
            "http://localhost:" + port + path + "?amount=3",
            SessionStartResponse.class
        );
    }

    @Then("the response status should be {int}")
    public void responseStatusCodeCheck(int statusCode) {
        assertEquals(HttpStatus.valueOf(statusCode), sessionStartResponse.getStatusCode());
    }

    @Then("the response should contain a session ID")
    public void the_response_should_contain_a_session_id() {
        assertNotNull(sessionStartResponse.getBody().getSessionId());
        assertFalse(sessionStartResponse.getBody().getSessionId().isEmpty());
    }

    @Then("the response should contain the retrieved questions")
    public void the_response_should_contain_the_retrieved_questions() {
        List<ClientQuestion> clientQuestions = sessionStartResponse.getBody().getQuestions();
        assertNotNull(clientQuestions);
        // Check whether each question is the one we specified earlier
        assertTrue(clientQuestions.get(0).getQuestion().contains("Olympics"));
        assertTrue(clientQuestions.get(1).getQuestion().contains("commercial"));
        assertTrue(clientQuestions.get(2).getQuestion().contains("fingers"));
        
    }

}
