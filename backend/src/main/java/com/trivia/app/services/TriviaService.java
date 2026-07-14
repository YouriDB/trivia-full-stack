package com.trivia.app.services;

import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.trivia.app.clients.OpentdbClient;
import com.trivia.app.models.Question;


@Service
public class TriviaService {

    private final String redisCacheQuestionsID = "QUESTIONS";
    
    private final OpentdbClient opentdbClient;
    private final RedisTemplate<String, Object> redisTemplate;

    // Spring Boot doet automatisch de dependency injecten omdat OpentdbClient een @Component is
    public TriviaService(OpentdbClient opentdbClient, RedisTemplate<String, Object> redisTemplate) {
        this.opentdbClient = opentdbClient;
        this.redisTemplate = redisTemplate;
    }

    public List<Question> getQuestions(Integer amount, String difficulty, String category, String type) throws Exception {
        List<Question> questions = opentdbClient.getQuestions(amount, difficulty, category, type);

        for (Question question : questions) {
            // Check if question is already in cache
            Question cachedQuestion = (Question)redisTemplate.opsForHash().get(redisCacheQuestionsID, question.question);

            if (cachedQuestion == null) {
                // Question not stored in cache, add to cache
                redisTemplate.opsForHash().put(redisCacheQuestionsID, question.question, question);
            }
        }

        return questions;
    }
        
}
