package com.trivia.app.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trivia.app.models.Question;
import com.trivia.app.services.TriviaService;

@RestController
public class TriviaController {

    public final int defaultAmount = 10;

    private final TriviaService triviaService;

    public TriviaController(TriviaService triviaService) {
        this.triviaService = triviaService;
    }

    // Spring Boot has built in error checking for Enum parsing,
    // this means we don't have to try/catch to parse enums from strings
    @GetMapping("/questions")
    public ResponseEntity<?> getQuestions(
        @RequestParam(required = false) Integer amount,
        @RequestParam(required = false) String difficulty,
        @RequestParam(required = false) String type,
        @RequestParam(required = false) String category
    ) {
        amount = (amount == null) ? defaultAmount : amount;
        // The opentdb API we use limits the number of questions to <=50 and must have at least 1 question
        if (amount < 1 || amount > 50) {
            return ResponseEntity
                    .badRequest()
                    .body("Amount of questions must be in the range 1 to 50.");
        }
        
        try {
            List<Question> triviaQuestions = triviaService.getQuestions(amount, difficulty, category, type);
            return ResponseEntity
                .ok(triviaQuestions);
        } catch (Exception e) {
            System.err.println("Bad Request from opentdb with exception: " + e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }

        
    }

    @GetMapping("/checkanswers")
    public String postCheckAnswers() {
        return "Answer Correct";
    }

}