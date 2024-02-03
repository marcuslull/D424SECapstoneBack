package com.mybackyard.backend.service.implementation;

import com.mybackyard.backend.model.User;
import com.mybackyard.backend.repository.UserRepository;
import com.mybackyard.backend.service.interfaces.ApiKeyService;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

@Service
public class ApikeyServiceImpl implements ApiKeyService {

    private final UserRepository userRepository;

    public ApikeyServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String generateNewKey() {
        String namespaceIdentifier = "mby";
        long now = LocalDateTime.now(ZoneId.of("UTC")).toInstant(ZoneOffset.UTC).toEpochMilli();
        UUID uuid = UUID.randomUUID();
        String uuidToStringWithUnderScores = uuid.toString().replace("-", "_");
        return uuidToStringWithUnderScores + "_" + now + namespaceIdentifier;
    }

    @Override
    public void parseDateTimeFromAPIKey(String apiKey) {
        String[] splitKey = apiKey.split("_");
        long epoch =  Long.parseLong(splitKey[5].replace("mby", ""));
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(epoch), ZoneId.of("UTC"));

        System.out.println(localDateTime);
    }

    @Override
    public String hashTheApIKey(String apiKey) {

        MessageDigest messageDigest;

        try {
            messageDigest = MessageDigest.getInstance("SHA3-512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] apiKeyBytes = apiKey.getBytes(StandardCharsets.UTF_8);
        byte[] digest = messageDigest.digest(apiKeyBytes);
        return new String(Hex.encode(digest));
    }

    @Override
    public void addKeyToUser(long userId, String hashedApiKey) {
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.orElse(null);
        if (user != null) {
            user.setApiKey(hashedApiKey);
            userRepository.save(user);
        }
    }

    @Override
    public long matchKeyToUserId(String apiKey) {
        Iterable<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getApiKey().equals(hashTheApIKey(apiKey))) {
                return user.getUserId();
            }
        }
        return 0;
    }
}
