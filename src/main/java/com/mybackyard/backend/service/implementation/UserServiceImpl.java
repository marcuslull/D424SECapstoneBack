package com.mybackyard.backend.service.implementation;

import com.mybackyard.backend.model.User;
import com.mybackyard.backend.repository.UserRepository;
import com.mybackyard.backend.service.interfaces.CompareAndUpdate;
import com.mybackyard.backend.service.interfaces.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CompareAndUpdate compareAndUpdate;

    public UserServiceImpl(UserRepository userRepository, CompareAndUpdate compareAndUpdate) {
        this.userRepository = userRepository;
        this.compareAndUpdate = compareAndUpdate;
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public long saveUser(User user) {
        return userRepository.save(user).getUserId();
    }

    @Override
    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(String id, User user) {
        user.setUserId(Long.parseLong(id));
        User baseUser = userRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
        try {
            return userRepository.save(compareAndUpdate.updateUser(baseUser, user));
        }
        catch (Exception e) {
            // TODO: figure out what to do with these exceptions - v.N
        }
        return baseUser;
    }
}
