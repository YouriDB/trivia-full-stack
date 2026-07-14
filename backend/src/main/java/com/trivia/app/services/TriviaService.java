package com.trivia.app.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trivia.app.clients.OpentdbClient;
import com.trivia.app.models.Question;


@Service
public class TriviaService {
    
    private final OpentdbClient opentdbClient;

    // Spring Boot doet automatisch de dependency injecten omdat OpentdbClient een @Component is
    public TriviaService(OpentdbClient opentdbClient) {
        this.opentdbClient = opentdbClient;
    }

    public List<Question> getQuestions(Integer amount, String difficulty, String category, String type) throws Exception {
        return opentdbClient.getQuestions(amount, difficulty, category, type);
    }
        
}
