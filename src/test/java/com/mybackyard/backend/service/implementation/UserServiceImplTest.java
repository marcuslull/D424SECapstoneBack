package com.mybackyard.backend.service.implementation;

import com.mybackyard.backend.model.User;
import com.mybackyard.backend.repository.UserRepository;
import com.mybackyard.backend.repository.YardRepository;
import com.mybackyard.backend.service.interfaces.ApiKeyService;
import com.mybackyard.backend.service.interfaces.CompareAndUpdate;
import com.mybackyard.backend.service.interfaces.UserService;
import com.mybackyard.backend.service.interfaces.YardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    private UserRepository userRepository;
    private CompareAndUpdate compareAndUpdate;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        compareAndUpdate = mock(CompareAndUpdate.class);
        userService = new UserServiceImpl(userRepository, compareAndUpdate);
    }

    @Test
    void getAllUsers() {
        User user = new User();
        user.setUserId(1);
        List<User> listOfUsers = List.of(user);
        when(userRepository.findAll()).thenReturn(listOfUsers);

        assertEquals(listOfUsers, userService.getAllUsers());
    }

    @Test
    void getUserById() {
        long userId = 1L;
        User user = new User();
        user.setUserId(userId);
        Optional<User> optionalUser = Optional.of(user);
        when(userRepository.findById(userId)).thenReturn(optionalUser);

        assertEquals(optionalUser, userService.getUserById(userId));
    }

    @Test
    void saveUser() {
        long userId = 1L;
        User user = new User();
        user.setUserId(userId);
        when(userRepository.save(user)).thenReturn(user);
        assertEquals(user.getUserId(), userService.saveUser(user));
    }

    @Test
    void deleteUserById() {
    }

    @Test
    void updateUser() throws IllegalAccessException {
        long userId = 1L;
        User user = new User();
        user.setUserId(userId);
        user.setFirst("first");
        Optional<User> baseUser = Optional.of(user);
        User patch = new User();
        patch.setUserId(userId);
        patch.setFirst("new first");
        User updatedUser = new User();
        updatedUser.setUserId(userId);
        updatedUser.setFirst("new first");
        when(userRepository.findById(userId)).thenReturn(baseUser);
        when(compareAndUpdate.updateUser(baseUser.get(), patch)).thenReturn(updatedUser);
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        assertEquals(updatedUser, userService.updateUser(String.valueOf(userId), patch));
    }
}