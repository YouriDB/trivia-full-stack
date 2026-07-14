package com.trivia.app.clients;

import java.net.URI;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trivia.app.models.Question;


/**
 * TriviaResponse object to parse json response into,
 * we put object only in this file as it's only used here.
 */
class TriviaResponse {
    
    @JsonProperty("response_code")
    public String responseCode;

    @JsonProperty("results")
    public List<Question> questions;

}

@Component
public class OpentdbClient {
    
    private final WebClient webClient;

    public OpentdbClient() {
        this.webClient = WebClient.builder()
                .baseUrl("https://opentdb.com")
                .build();
    }
    
    /**
     * Retrieve questions from the opentdb API using all available params.
     * 
     * @param amount Amount of questions to retrieve.
     * @param difficulty Difficulty level of each question, default is all difficulties.
     * @param category Category of each question, default is all categories.
     * @param type Type of questions either boolean or multiple choice, default is both types.
     * @return Returns a list of questions including the correct and wrong answers.
     * @throws Exception if amount is incorrect or the query fails.
     */
    public List<Question> getQuestions(int amount, String difficulty, String category, String type) throws Exception {
        // If any precondition doesn't hold true, throw Illegal Argument.
        if (amount < 0 || amount > 50) {
            throw new IllegalArgumentException("Amount must be in the range 1 to 50.");
        }

        // Add any parameters
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromPath("/api.php")
                .queryParam("amount", amount);

        if (difficulty != null) {
            builder.queryParam("difficulty", difficulty);
        }

        if (category != null) {
            builder.queryParam("category", category);
        }

        if (type != null) {
            builder.queryParam("type", type);
        }

        URI uri = builder.build().toUri();

        // GET request to API, will propogate exception.
        return webClient
                .get()
                .uri(uri.toString())
                .retrieve()
                .bodyToMono(TriviaResponse.class)
                .block()
                .questions;
    }
}