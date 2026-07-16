package com.trivia.app.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.trivia.app.cache.CacheHelper;
import com.trivia.app.clients.OpentdbClient;
import com.trivia.app.mocking.OpentdbClientMock;
import com.trivia.app.models.ClientAnswer;
import com.trivia.app.models.ClientQuestion;
import com.trivia.app.models.Question;
import com.trivia.app.models.Session;
import com.trivia.app.models.SessionEndResponse;
import com.trivia.app.models.SessionStartResponse;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import tools.jackson.databind.ObjectMapper;

public class StepDefinitions {
    
    // Used to mock the opentdb client
    private OpentdbClientMock opentdbClientMock;
    // Used to for integration tests
    private TestRestTemplate restTemplate;
    // Used to deserialize string responses to objects
    private ObjectMapper objectMapper;
    // Used to interface with the Redis cache
    private CacheHelper cacheHelper;

    @LocalServerPort
    private int port;

    private ResponseEntity<String> response;
    private String sessionId;

    public StepDefinitions(
        OpentdbClientMock opentdbClientMock,
        TestRestTemplate restTemplate,
        CacheHelper cacheHelper
    ) {
        this.opentdbClientMock = opentdbClientMock;
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper();
        this.cacheHelper = cacheHelper;
    }

    @Given("the opentdb api returns questions")
    public void the_opentdb_api_returns_questions() throws Exception {
        opentdbClientMock.mock3QuestionsAllAny();
    }

    @When("the client requests questions from {string}")
    public void the_client_request_questions_from(String path) {
        this.response = restTemplate.exchange(
            "http://localhost:" + port + path,
            HttpMethod.GET,
            null,
            String.class
        );
    }

    @Then("the response status should be {int}")
    public void the_response_status_should_be(int statusCode) {
        assertNotNull(response);
        assertEquals(HttpStatus.valueOf(statusCode), response.getStatusCode());
    }

    @Then("the response should contain a session ID")
    public void the_response_should_contain_a_session_id() {
        assertNotNull(response);
        SessionStartResponse sessionResponse = objectMapper.readValue(response.getBody(), SessionStartResponse.class);
        assertNotNull(sessionResponse.getSessionId());
        assertFalse(sessionResponse.getSessionId().isEmpty());
    }

    @Then("the response should contain the retrieved questions")
    public void the_response_should_contain_the_retrieved_questions() {
        assertNotNull(response);
        SessionStartResponse sessionResponse = objectMapper.readValue(response.getBody(), SessionStartResponse.class);
        List<ClientQuestion> clientQuestions = sessionResponse.getQuestions();
        assertNotNull(clientQuestions);
        // Check whether each question has content
        assertFalse(clientQuestions.get(0).getQuestion().isEmpty());
        assertFalse(clientQuestions.get(1).getQuestion().isEmpty());
        assertFalse(clientQuestions.get(2).getQuestion().isEmpty());
    }

    @Then("the questions should be cached")
    public void the_questions_should_be_cached() {
        assertNotNull(response);
        SessionStartResponse sessionResponse = objectMapper.readValue(response.getBody(), SessionStartResponse.class);
        String sessionId = sessionResponse.getSessionId();
        Session session = cacheHelper.getSession(sessionId);
        assertNotNull(session);
        assertEquals(sessionId, session.getSessionId());
    }

    @Given("the client requested questions from {string}")
    public void the_client_requested_questions_from(String path) {
        this.response = restTemplate.exchange(
            "http://localhost:" + port + path,
            HttpMethod.GET,
            null,
            String.class
        );

        SessionStartResponse sessionResponse = objectMapper.readValue(response.getBody(), SessionStartResponse.class);
        this.sessionId = sessionResponse.getSessionId();
    }

    @When("the client submits correct answers to {string}")
    public void the_client_submits_correct_answers_to(String path) {
        List<ClientAnswer> clientAnswers = opentdbClientMock.get3CorrectAnswers();
        HttpEntity<List<ClientAnswer>> entity = new HttpEntity<>(clientAnswers);

        this.response = restTemplate.exchange(
            "http://localhost:" + port + path + "?sessionId=" + this.sessionId,
            HttpMethod.POST,
            entity,
            String.class
        );
    }

    @When("the client submits incorrect answers to {string}")
    public void the_client_submits_incorrect_answers_to(String path) {
        List<ClientAnswer> clientAnswers = opentdbClientMock.get3IncorrectAnswers();
        HttpEntity<List<ClientAnswer>> entity = new HttpEntity<>(clientAnswers);

        this.response = restTemplate.exchange(
            "http://localhost:" + port + path + "?sessionId=" + this.sessionId,
            HttpMethod.POST,
            entity,
            String.class
        );
    }

    @Then("the response should contain a grading")
    public void the_response_should_contain_a_grading() {
        assertNotNull(response);
        SessionEndResponse sessionResponse = objectMapper.readValue(response.getBody(), SessionEndResponse.class);
        assertNotNull(sessionResponse);
        assertTrue(sessionResponse.getGrading().size() > 0);
    }

    @Then("the answers should be correct")
    public void the_answers_should_be_correct() {
        assertNotNull(response);
        SessionEndResponse sessionResponse = objectMapper.readValue(response.getBody(), SessionEndResponse.class);
        assertNotNull(sessionResponse);
        for (Map.Entry<String, Boolean> pair : sessionResponse.getGrading().entrySet()) {
            assertTrue(pair.getValue());   
        };
    }

    @Then("the answers should be incorrect")
    public void the_answers_should_be_incorrect() {
        assertNotNull(response);
        SessionEndResponse sessionResponse = objectMapper.readValue(response.getBody(), SessionEndResponse.class);
        assertNotNull(sessionResponse);
        for (Map.Entry<String, Boolean> pair : sessionResponse.getGrading().entrySet()) {
            assertFalse(pair.getValue());   
        };

    }

    @Then("the cached session should be deleted")
    public void the_cached_session_is_deleted() {
        Session session = cacheHelper.getSession(sessionId);
        assertNull(session);
    }

}
