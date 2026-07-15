package com.trivia.app.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trivia.app.models.ClientAnswer;
import com.trivia.app.models.SessionEndResponse;
import com.trivia.app.models.SessionStartResponse;
import com.trivia.app.services.TriviaService;

@RestController
public class TriviaController {

    private final TriviaService triviaService;

    public TriviaController(TriviaService triviaService) {
        this.triviaService = triviaService;
    }

    // Spring Boot has built in error checking for Enum parsing,
    // this means we don't have to try/catch to parse enums from strings
    @GetMapping("/questions")
    public ResponseEntity<?> getQuestions(
        @RequestParam(required = true) Integer amount,
        @RequestParam(required = false) String difficulty,
        @RequestParam(required = false) String type,
        @RequestParam(required = false) String category
    ) {
        // The opentdb API we use limits the number of questions to <=50 and must have at least 1 question
        if (amount < 1 || amount > 50) {
            return ResponseEntity
                    .badRequest()
                    .body("Amount of questions must be in the range 1 to 50.");
        }
        
        try {
            SessionStartResponse sessionStartResponse = triviaService.startSession(amount, difficulty, category, type);
            return ResponseEntity.ok(sessionStartResponse);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }

        
    }

    @PostMapping("/checkanswers")
    public ResponseEntity<?> postCheckAnswers(
        @RequestParam(required = true) String sessionId,
        @RequestBody List<ClientAnswer> clientAnswers
    ) {
        try {
            SessionEndResponse sessionEndResponse = triviaService.endSession(sessionId, clientAnswers);
            return ResponseEntity.ok(sessionEndResponse);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

}