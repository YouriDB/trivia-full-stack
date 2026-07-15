package com.trivia.app.services;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.trivia.app.clients.OpentdbClient;
import com.trivia.app.models.ClientAnswer;
import com.trivia.app.models.ClientQuestion;
import com.trivia.app.models.Question;
import com.trivia.app.models.SessionStartResponse;
import com.trivia.app.models.Session;
import com.trivia.app.models.SessionEndResponse;


@Service
public class TriviaService {

    // Keys for redis cache, also added a max duration so the cache doesn't infinitely keep data stored
    private final String questionsCachePrefix = "QUESTIONS_";
    private final Long maxCacheTime = Duration.ofMinutes(35).toSeconds();
    
    private final OpentdbClient opentdbClient;
    private final RedisTemplate<String, Object> redisTemplate;

    // Spring Boot doet automatisch de dependency injecten omdat OpentdbClient een @Component is
    public TriviaService(OpentdbClient opentdbClient, RedisTemplate<String, Object> redisTemplate) {
        this.opentdbClient = opentdbClient;
        this.redisTemplate = redisTemplate;
    }

    /**
     * This function requests questions from the opentdb API,
     * parses them into a response object to return to the client,
     * and stores it into the Redis cache to be retrieved later.
     * 
     * @param amount amount of questions to start the session with.
     * @param difficulty difficulty of the questions, default is any for each question
     * @param category category of the questions, default is any for each question
     * @param type type of the questions, default is any for each question
     * @return SessionStartResponse including the session Id and requested questions
     * @throws Exception when opentdb client throws an exception
     */
    public SessionStartResponse startSession(int amount, String difficulty, String category, String type) throws Exception {
        // Get questions from API
        List<Question> questions = opentdbClient.getQuestions(amount, difficulty, category, type);

        // Randomly generate sessionId
        String sessionId = UUID.randomUUID().toString();

        Session session = new Session();
        session.setSessionId(sessionId);
        session.setQuestions(questions);
        session.setExpirationInSeconds(maxCacheTime);

        // Save the session into cache
        redisTemplate.opsForValue().set(questionsCachePrefix + sessionId, session);

        return createSessionResponse(sessionId, questions);
    }

    /**
     * Retrieves the session from the Redis cache using sessionId and checks corresponding
     * clientAnswers with the correct answers.
     * 
     * @param sessionId sessionId used to retrieve the correct answers from cache
     * @param clientAnswers client question answers to compare with the correct answers
     * @return SessionEndResponse to return the grading of the answers to the client
     * @throws Exception when the cached session does not exist
     */
    public SessionEndResponse endSession(String sessionId, List<ClientAnswer> clientAnswers) throws Exception {
        // Get cached session from Redis
        Session cachedSession = (Session) redisTemplate.opsForValue().get(questionsCachePrefix + sessionId);
        if (cachedSession == null) {
            throw new Exception("Client session does not exist");
        }

        List<Question> questions = cachedSession.getQuestions();

        // Set the grading for how many questions they got correct and wrong in one go
        SessionEndResponse sessionEndResponse = new SessionEndResponse();
        sessionEndResponse.setGrading(questions, clientAnswers);

        // After session end remove from cache
        redisTemplate.opsForValue().getAndDelete(sessionId);
        return sessionEndResponse;
    }

    private SessionStartResponse createSessionResponse(String sessionId, List<Question> questions) {
        SessionStartResponse sessionResponse = new SessionStartResponse();
        sessionResponse.setSessionId(sessionId);
        List<ClientQuestion> clientQuestions = new ArrayList<>();

        // Parse each question into the client question format
        for (Question question : questions) {
            ClientQuestion clientQuestion = new ClientQuestion();
            clientQuestion.setQuestion(question.question);
            clientQuestion.setAnswers(question.correctAnswer, question.incorrectAnswers);
            clientQuestions.add(clientQuestion);
        }

        sessionResponse.setQuestions(clientQuestions);
        return sessionResponse;
    }
        
}
