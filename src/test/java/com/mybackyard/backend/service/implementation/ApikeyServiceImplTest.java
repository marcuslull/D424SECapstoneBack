package com.mybackyard.backend.service.implementation;

import com.mybackyard.backend.repository.UserRepository;
import com.mybackyard.backend.service.interfaces.ApiKeyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ApikeyServiceImplTest {

    private UserRepository userRepository;
    private ApiKeyService apiKeyService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        apiKeyService = new ApikeyServiceImpl(userRepository);
    }

    @Test
    void generateNewKey() {
        String resultKey = apiKeyService.generateNewKey();

        assertTrue(resultKey.length() > 50);
    }

    @Test
    void parseDateTimeFromAPIKey() {
    }

    @Test
    void hashTheApIKey() {
        String apiKey = "265fdf0e_9390_4861_b054_6042a5060100_1703114987103mby";
        String hash = apiKeyService.hashTheApIKey(apiKey);

        assertTrue(hash.length() > 120);
    }

    @Test
    void addKeyToUser() {
    }

    @Test
    void matchKeyToUserId() {
    }
}