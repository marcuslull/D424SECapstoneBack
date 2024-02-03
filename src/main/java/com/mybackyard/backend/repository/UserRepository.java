package com.mybackyard.backend.repository;

import com.mybackyard.backend.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository  extends CrudRepository<User, Long> {
    User findByApiKey(String apiKey);
}
