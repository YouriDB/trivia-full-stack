package com.trivia.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TriviaController {

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}