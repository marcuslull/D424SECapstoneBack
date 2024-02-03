package com.mybackyard.backend.service.interfaces;

import com.mybackyard.backend.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();

    Optional<User> getUserById(long id);

    long saveUser(User user);

    User updateUser(String id, User user);

    void deleteUserById(long id);
}
