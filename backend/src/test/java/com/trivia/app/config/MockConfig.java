package com.trivia.app.config;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.trivia.app.clients.OpentdbClient;

@TestConfiguration
public class MockConfig {

    @Bean
    public OpentdbClient opentdbClient() {
        return Mockito.mock(OpentdbClient.class);
    }

}