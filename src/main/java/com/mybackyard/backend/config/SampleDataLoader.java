package com.mybackyard.backend.config;

import com.mybackyard.backend.model.User;
import com.mybackyard.backend.repository.UserRepository;
import com.mybackyard.backend.service.interfaces.ApiKeyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SampleDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ApiKeyService apiKeyService;

    @Value("${api_key_1}")
    String key1;

    @Value("${api_key_2}")
    String key2;


    public SampleDataLoader(UserRepository userRepository, ApiKeyService apiKeyService) {
        this.userRepository = userRepository;
        this.apiKeyService = apiKeyService;
    }

    @Override
    public void run(String... args) {

        User user1 = new User();
        User user2 = new User();

        user1.setFirst("John");
        user1.setLast("Doe");
        user2.setFirst("Jane");
        user2.setLast("Doe");
        user1.setEmail("johnd@example.com");
        user2.setEmail("janed@example.com");

        userRepository.saveAll(List.of(user1, user2));

        String hashedApiKey1 = apiKeyService.hashTheApIKey(key1);
        String hashedApiKey2 = apiKeyService.hashTheApIKey(key2);
        apiKeyService.addKeyToUser(1, hashedApiKey1); // TODO: switch this to the above hashed key - PROD
        apiKeyService.addKeyToUser(2, hashedApiKey2);
    }
}
