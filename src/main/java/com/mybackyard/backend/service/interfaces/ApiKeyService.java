package com.mybackyard.backend.service.interfaces;

public interface ApiKeyService {

    String generateNewKey();
    void parseDateTimeFromAPIKey(String apiKey);
    String hashTheApIKey(String apiKey);
    void addKeyToUser(long userId, String apiKey);
    long matchKeyToUserId(String apiKey);
}
